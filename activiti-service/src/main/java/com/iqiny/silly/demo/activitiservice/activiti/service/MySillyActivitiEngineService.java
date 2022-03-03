package com.iqiny.silly.demo.activitiservice.activiti.service;



import com.iqiny.silly.demo.activitiservice.activiti.entity.MySillyActivitiTask;
import com.iqiny.silly.demo.activitiservice.activiti.entity.MySillyMasterTask;
import com.iqiny.silly.demo.activitiservice.activiti.entity.SillyMasterVO;
import org.activiti.engine.*;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.db.IbatisVariableTypeHandler;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.ReadOnlyProcessDefinition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.util.ReflectUtil;
import org.activiti.engine.impl.variable.VariableType;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;


@Component
public class MySillyActivitiEngineService implements ApplicationContextAware, InitializingBean {

    public static final String GROUP_USER_ID_PREFIX = "group_user_id_prefix@@";

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initSqlSessionFactory(applicationContext);
        otherInit(applicationContext);
    }

    private final static Log log = LogFactory.getLog(MySillyActivitiEngineService.class);

    private SqlSessionFactory sqlSessionFactory;
    private final String DEFAULT_MYBATIS_MAPPING_FILE = "iqiny/silly/mappings.xml";

    protected void initSqlSessionFactory(ApplicationContext context) {
        DataSource dataSource = context.getBean(DataSource.class);
        TransactionFactory transactionFactory = null;
        try{
            transactionFactory = context.getBean(TransactionFactory.class);
        } catch (BeansException e){
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
            throw new RuntimeException("Error while building ibatis SqlSessionFactory: " + e.getMessage(), e);
        }
    }



    public List<MySillyMasterTask> getDoingMasterTask(String category, String userId, Set<String> allGroupId) {
        return getMyDoingMasterTaskId(category, userId, null, allGroupId);
    }


    public List<MySillyMasterTask> getHistoryMasterTask(String category, String userId) {
        // 不在流程引擎中做历史数据查询，而是根据业务履历进行查询
        return new ArrayList<>();
    }


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
        return "com.iqiny.silly.demo.activitiservice.activiti.entity.MySillyMasterTask.";
    }

    protected ManagementService managementService;
    protected RuntimeService runtimeService;
    protected HistoryService historyService;
    protected TaskService taskService;
    protected RepositoryService repositoryService;


    public void otherInit(ApplicationContext context) {
        this.managementService = context.getBean(ManagementService.class);
        this.runtimeService = context.getBean(RuntimeService.class);
        this.historyService = context.getBean(HistoryService.class);
        this.taskService = context.getBean(TaskService.class);
        this.repositoryService = context.getBean(RepositoryService.class);
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


    public String start(SillyMasterVO master, Map<String, Object> variableMap) {
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


    public void complete(MySillyActivitiTask task, String userId, Map<String, Object> variableMap) {
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


    public List<MySillyActivitiTask> changeTask(MySillyActivitiTask task, String nodeKey, String userId) {
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


    public List<MySillyActivitiTask> findTaskByProcessInstanceId(String processInstanceId) {
        if (StringUtils.isEmpty(processInstanceId)) {
            throw new RuntimeException("查询任务列表，流程实例ID不可为空！");
        }
        return convertor(taskService.createTaskQuery().processInstanceId(processInstanceId).list());
    }


    public MySillyActivitiTask findTaskById(String taskId) {
        if (StringUtils.isEmpty(taskId)) {
            return null;
        }
        return convertor(taskService.createTaskQuery().taskId(taskId).singleResult());
    }


    public String getBusinessKey(String processInstanceId) {
        final HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        return historicProcessInstance.getBusinessKey();
    }


    public String getBusinessKeyByTaskId(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        return getBusinessKey(task.getProcessInstanceId());
    }


    public Long getTaskDueTime(MySillyActivitiTask task) {
        if (task == null || StringUtils.isEmpty(task.getId())) {
            return 0L;
        }

        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(task.getId()).singleResult();
        return historicTaskInstance.getDurationInMillis();
    }


    public List<String> getTaskUserIds(MySillyActivitiTask task) {
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
                    ids.add(GROUP_USER_ID_PREFIX + groupId);
                } else if (StringUtils.isNotEmpty(userId)) {
                    ids.add(userId);
                }
            }
        }
        return ids;
    }


    public void endProcessByProcessInstanceId(String processInstanceId, String userId) {
        List<MySillyActivitiTask> tasks = findTaskByProcessInstanceId(processInstanceId);
        if (!tasks.isEmpty()) {
            endProcessByProcessInstanceId(processInstanceId, tasks.get(0), userId);
        }
    }

    public void endProcessByProcessInstanceId(String actProcessId, MySillyActivitiTask task, String userId) {
        if (task != null) {
            changeTask(task, "end", userId);
        }
        if (actProcessId != null) {
            List<MySillyActivitiTask> tasks = findTaskByProcessInstanceId(actProcessId);
            if (!tasks.isEmpty()) {
                endProcessByProcessInstanceId(actProcessId, tasks.get(0).getId());
            }
        }
    }


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


    public void deleteProcessInstance(String processInstanceId, String deleteReason) {
        runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
    }


    public List<HistoricProcessInstance> findProcessInstanceByMasterId(String masterId) {
        return historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(masterId).list();
    }


    public List<MySillyMasterTask> findMyTaskByMasterId(String category, String userId, String masterId, Set<String> allGroupId) {
        return getMyDoingMasterTaskId(category, userId, masterId, allGroupId);
    }


    public List<MySillyActivitiTask> findTaskByMasterId(String masterId) {
        final List<HistoricProcessInstance> processInstances = findProcessInstanceByMasterId(masterId);
        List<MySillyActivitiTask> taskList = new ArrayList<>();
        for (HistoricProcessInstance processInstance : processInstances) {
            taskList.addAll(findTaskByProcessInstanceId(processInstance.getId()));
        }
        return taskList;
    }



    public void changeUser(String taskId, String userId) {
        taskService.setAssignee(taskId, userId);
    }


    public void addUser(String taskId, String userId) {
        taskService.addCandidateUser(taskId, userId);
    }


    public void deleteUser(String taskId, String userId) {
        taskService.deleteCandidateUser(taskId, userId);
    }


    public void deleteTask(String taskId) {
        taskService.deleteTask(taskId);
    }

}
