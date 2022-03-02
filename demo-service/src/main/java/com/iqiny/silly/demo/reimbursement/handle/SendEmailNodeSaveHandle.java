package com.iqiny.silly.demo.reimbursement.handle;

import com.iqiny.silly.core.config.SillyCategoryConfig;
import com.iqiny.silly.core.savehandle.SillyNodeSourceData;
import com.iqiny.silly.core.savehandle.node.BaseSillyNodeSaveHandle;
import org.springframework.stereotype.Component;

@Component
public class SendEmailNodeSaveHandle extends BaseSillyNodeSaveHandle {

    @Override
    public String name() {
        return "sendEmail";
    }

    @Override
    protected boolean canDo(SillyNodeSourceData sourceData) {
        return sourceData != null;
    }

    @Override
    protected void handle(SillyCategoryConfig sillyConfig, SillyNodeSourceData sourceData) {
        // 发送邮件逻辑
        System.out.println("假装发送邮件地址：" + sourceData.getVariableText("email"));
        System.out.println("假装发送邮件内容：" + sourceData.getVariableText("emailInfo"));
    }

}
