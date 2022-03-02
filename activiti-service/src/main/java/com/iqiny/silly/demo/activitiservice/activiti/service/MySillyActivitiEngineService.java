package com.iqiny.silly.demo.activitiservice.activiti.service;


import com.iqiny.silly.activiti.RejectToAnyWhereTaskCmd;
import com.iqiny.silly.activiti.SillyActivitiTask;
import com.iqiny.silly.common.SillyConstant;
import com.iqiny.silly.common.exception.SillyException;
import com.iqiny.silly.common.util.StringUtils;
import com.iqiny.silly.core.base.SillyContext;
import com.iqiny.silly.core.base.core.SillyMaster;
import com.iqiny.silly.core.engine.SillyEngineService;
import com.iqiny.silly.core.engine.SillyTask;
import com.iqiny.silly.core.group.BaseSillyTaskGroupHandle;
import com.iqiny.silly.core.read.MySillyMasterTask;
import com.iqiny.silly.demo.activitiservice.activiti.entity.MySillyActivitiTask;
import org.activiti.engine.*;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.db.IbatisVariableTypeHandler;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.ReadOnlyProcessDefinition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.util.ReflectUtil;
import org.activiti.engine.impl.variable.VariableType;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;


@Component
public class MySillyActivitiEngineService implements SillyEngineService<MySillyActivitiTask, MySillyMasterTask> {

    @Autowired
    private SillyContext sillyContext;

    private final static Log log = LogFactory.getLog(MySillyActivitiEngineService.class);

    private SqlSessionFactory sqlSessionFactory;
    private final String DEFAULT_MYBATIS_MAPPING_FILE = "iqiny/silly/mappings.xml";

    protected void initSqlSessionFactory(SillyContext sillyContext) {
        DataSource dataSource = sillyContext.getBean(DataSource.class);
        TransactionFactory transactionFactory = sillyContext.getBean(TransactionFactory.class);
        if (transactionFactory == null) {
            transactionFactory = new SpringManagedTransactionFactory();
        }

        try (InputStream inputStream = ReflectUtil.getResourceAsStream(DEFAULT_MYBATIS_MAPPING_FILE)) {
            Environment environment = new Environment("default", transactionFactory, dataSource);
            Reader reader = new InputStreamReader(inputStream);
            Properties properties = new Properties();
            XMLConfigBuilder parser = new XMLConfigBuilder(reader, null, properties);
            Configuration configuration = parser.getConfiguration();
            configuration.setEnvironment(environment);
            configuration.getTypeHandlerRegistry().register(VariableType.class, JdbcType.VARCHAR, new IbatisVariableTypeHandler());
            configuration = parser.parse();
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        } catch (Exception e) {
            throw new SillyException("Error while building ibatis SqlSessionFactory: " + e.getMessage(), e);
        }
    }

    @Override
    public void init() {
        init(sillyContext);
    }

    public void init(SillyContext sillyContext) {
        initSqlSessionFactory(sillyContext);
        otherInit(sillyContext);
    }

    @Override
    public List<MySillyMasterTask> getDoingMasterTask(String category, String userId, Set<String> allGroupId) {
        return getMyDoingMasterTaskId(category, userId, null, allGroupId);
    }

    @Override
    public List<MySillyMasterTask> getHistoryMasterTask(String category, String userId) {
        Map<String, Object> param = new HashMap<>();
        param.put("category", category);
        param.put("userId", userId);

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            return sqlSession.selectList(namespace() + "getHistoryMasterTask", param);
        } catch (Exception e) {
            log.warn("数据查询异常" + e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    @Override
    public List<MySillyMasterTask> getMyDoingMasterTaskId(String category, String userId, String masterId, Set<String> allGroupId) {
        Map<String, Object> param = new HashMap<>();
        param.put("allGroupId", allGroupId);
        param.put("category", category);
        param.put("businessKey", masterId);
        param.put("userId", userId);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            return sqlSession.selectList(namespace() + "getDoingMasterTask", param);
        } catch (Exception e) {
            log.warn("数据查询异常" + e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    private String namespace() {
        return "com.iqiny.silly.core.read.MySillyMasterTask.";
    }

    protected ManagementService managementService;
    protected RuntimeService runtimeService;
    protected HistoryService historyService;
    protected TaskService taskService;
    protected RepositoryService repositoryService;


    public void otherInit(SillyContext sillyContext) {
        this.managementService = sillyContext.getBean(ManagementService.class);
        this.runtimeService = sillyContext.getBean(RuntimeService.class);
        this.historyService = sillyContext.getBean(HistoryService.class);
        this.taskService = sillyContext.getBean(TaskService.class);
        this.repositoryService = sillyContext.getBean(RepositoryService.class);
    }

    public MySillyActivitiTask convertor(Task task) {
        if (task == null) {
            return null;
        }

        return new MySillyActivitiTask(task);
    }

    public List<MySillyActivitiTask> convertor(List<Task> tasks) {
        if (tasks == null) {
            return null;
        }

        List<MySillyActivitiTask> taskList = new ArrayList<>();
        for (Task task : tasks) {
            taskList.add(new MySillyActivitiTask(task));
        }
        return taskList;
    }

    @Override
    public String start(SillyMaster master, Map<String, Object> variableMap) {
        if (StringUtils.isEmpty(master.processKey())) {
            throw new RuntimeException("流程启动时流程KEY 不可为空！");
        }
        if (StringUtils.isEmpty(master.getId())) {
            throw new RuntimeException("流程启动时 业务主键 不可为空！");
        }
        if (variableMap == null) {
            variableMap = new HashMap<>();
        }
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(master.processKey(), master.getId(), variableMap);
        return processInstance.getId();
    }

    @Override
    public void complete(SillyTask task, String userId, Map<String, Object> variableMap) {
        String taskId = task.getId();
        if (StringUtils.isEmpty(task.getId())) {
            throw new RuntimeException("当前执行的任务ID获取失败");
        }
        if (task.getAssignee() == null) {
            // 认领任务
            taskService.claim(taskId, userId);
        }
        // 完成任务
        taskService.complete(taskId, variableMap);
    }

    @Override
    public List<MySillyActivitiTask> changeTask(SillyTask task, String nodeKey, String userId) {
        String processInstanceId = task.getProcessInstanceId();
        String taskId = task.getId();
        if (StringUtils.isEmpty(taskId) || StringUtils.isEmpty(processInstanceId)) {
            throw new RuntimeException("任务ID/流程实例ID 不可为空！");
        }
        // 目标节点
        ActivityImpl pointActivity = findActivitiImpl(taskId, nodeKey);

        Map<String, Object> value = new HashMap<>();
        if (StringUtils.isEmpty(userId)) {
            value = makeUserVarMap(pointActivity, userId);
        }

        // 当前节点
        ActivityImpl currActivity = findActivitiImpl(taskId, null);

        managementService.executeCommand(
                new RejectToAnyWhereTaskCmd(task.getExecutionId(), pointActivity, value, currActivity, findProcessDefinitionEntityByTaskId(taskId))
        );

        return findTaskByProcessInstanceId(processInstanceId);
    }

    @Override
    public List<MySillyActivitiTask> findTaskByProcessInstanceId(String processInstanceId) {
        if (StringUtils.isEmpty(processInstanceId)) {
            throw new SillyException("查询任务列表，流程实例ID不可为空！");
        }
        return convertor(taskService.createTaskQuery().processInstanceId(processInstanceId).list());
    }

    @Override
    public MySillyActivitiTask findTaskById(String taskId) {
        if (StringUtils.isEmpty(taskId)) {
            return null;
        }
        return convertor(taskService.createTaskQuery().taskId(taskId).singleResult());
    }

    @Override
    public String getBusinessKey(String processInstanceId) {
        final HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        return historicProcessInstance.getBusinessKey();
    }

    @Override
    public String getBusinessKeyByTaskId(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        return getBusinessKey(task.getProcessInstanceId());
    }

    @Override
    public Long getTaskDueTime(SillyTask task) {
        if (task == null || StringUtils.isEmpty(task.getId())) {
            return 0L;
        }

        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(task.getId()).singleResult();
        return historicTaskInstance.getDurationInMillis();
    }

    @Override
    public List<String> getTaskUserIds(SillyTask task) {
        final String taskId = task.getId();
        return getTaskUserIds(taskId);
    }

    public List<String> getTaskUserIds(String taskId) {
        List<String> ids = new ArrayList<>();
        if (StringUtils.isNotEmpty(taskId)) {
            List<IdentityLink> identityLinkList = taskService.getIdentityLinksForTask(taskId);
            for (IdentityLink link : identityLinkList) {
                String groupId = link.getGroupId();
                String userId = link.getUserId();
                if (StringUtils.isNotEmpty(groupId)) {
                    ids.add(BaseSillyTaskGroupHandle.GROUP_USER_ID_PREFIX + groupId);
                } else if (StringUtils.isNotEmpty(userId)) {
                    ids.add(userId);
                }
            }
        }
        return ids;
    }

    @Override
    public void endProcessByProcessInstanceId(String processInstanceId, String userId) {
        List<MySillyActivitiTask> tasks = findTaskByProcessInstanceId(processInstanceId);
        if (!tasks.isEmpty()) {
            endProcessByProcessInstanceId(processInstanceId, tasks.get(0), userId);
        }
    }

    public void endProcessByProcessInstanceId(String actProcessId, MySillyActivitiTask task, String userId) {
        if (task != null) {
            changeTask(task, SillyConstant.ActivitiNode.KEY_END, userId);
        }
        if (actProcessId != null) {
            List<MySillyActivitiTask> tasks = findTaskByProcessInstanceId(actProcessId);
            if (!tasks.isEmpty()) {
                endProcessByProcessInstanceId(actProcessId, tasks.get(0).getId());
            }
        }
    }

    @Override
    public String getActKeyNameByProcessInstanceId(String processInstanceId) {
        final String processDefinitionId = getProcessDefinitionIdByProcessInstanceId(processInstanceId);
        if (StringUtils.isEmpty(processDefinitionId)) {
            return null;
        }
        final ReadOnlyProcessDefinition deployedProcessDefinition = ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(processDefinitionId);
        if (deployedProcessDefinition != null) {
            return deployedProcessDefinition.getKey();
        }
        return null;
    }

    // ======================================= 工作流服务 内部方法 =============================================

    private ActivityImpl findActivitiImpl(String taskId, String nodeKey) {
        // 取得流程定义 11
        ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);

        // 获取当前活动节点ID
        if (nodeKey == null || "".equals(nodeKey)) {
            nodeKey = findTaskById(taskId).getTaskDefinitionKey();
        }
        // 根据节点ID，获取对应的活动节点
        return processDefinition.findActivity(nodeKey);
    }

    private ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(String taskId) {
        // 取得流程定义
        return (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(taskService.createTaskQuery().taskId(taskId).singleResult()
                        .getProcessDefinitionId());
    }

    private Map<String, Object> makeUserVarMap(ActivityImpl pointActivity, String userId) {
        if (pointActivity != null && pointActivity.getActivityBehavior() instanceof UserTaskActivityBehavior) {
            // 设置节点处置人信息
            UserTaskActivityBehavior behavior = (UserTaskActivityBehavior) pointActivity.getActivityBehavior();
            Set<Expression> set = behavior.getTaskDefinition().getCandidateUserIdExpressions();
            Map<String, Object> value = new HashMap<>();
            for (Expression expression : set) {
                String et = expression.getExpressionText();
                if (et.length() > 3) {
                    // 设置下一步操作人
                    String userKey = et.substring(2, et.length() - 1);
                    value.put(userKey, userId);
                }
            }
            return value;
        }
        return new HashMap<>();
    }

    private String getProcessDefinitionIdByProcessInstanceId(String processInstanceId) {
        if (StringUtils.isEmpty(processInstanceId)) {
            return null;
        }
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String processDefinitionId = "";
        if (processInstance == null) {
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            processDefinitionId = historicProcessInstance.getProcessDefinitionId();
        } else {
            processDefinitionId = processInstance.getProcessDefinitionId();
        }
        return processDefinitionId;
    }

    @Override
    public void deleteProcessInstance(String processInstanceId, String deleteReason) {
        runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
    }


    public List<HistoricProcessInstance> findProcessInstanceByMasterId(String masterId) {
        return historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(masterId).list();
    }

    @Override
    public List<MySillyMasterTask> findMyTaskByMasterId(String category, String userId, String masterId, Set<String> allGroupId) {
        return getMyDoingMasterTaskId(category, userId, masterId, allGroupId);
    }

    @Override
    public List<MySillyActivitiTask> findTaskByMasterId(String masterId) {
        final List<HistoricProcessInstance> processInstances = findProcessInstanceByMasterId(masterId);
        List<MySillyActivitiTask> taskList = new ArrayList<>();
        for (HistoricProcessInstance processInstance : processInstances) {
            taskList.addAll(findTaskByProcessInstanceId(processInstance.getId()));
        }
        return taskList;
    }


    @Override
    public void changeUser(String taskId, String userId) {
        taskService.setAssignee(taskId, userId);
    }

    @Override
    public void addUser(String taskId, String userId) {
        taskService.addCandidateUser(taskId, userId);
    }

    @Override
    public void deleteUser(String taskId, String userId) {
        taskService.deleteCandidateUser(taskId, userId);
    }

    @Override
    public void deleteTask(String taskId) {
        taskService.deleteTask(taskId);
    }
}
