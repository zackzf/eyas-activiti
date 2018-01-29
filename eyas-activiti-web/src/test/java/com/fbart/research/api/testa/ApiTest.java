package com.fbart.research.api.testa;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.junit.Test;

public class ApiTest {
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * ACT_RE_DEPLOYMENT：部署主表
     * ACT_RE_PROCDEF：扩展表，存在deployment_id外键，存在版本号
     * ACT_GE_BYTEARRAY：存在deployment_id外键
     */
    @Test
    public void test1() {
        DeploymentBuilder builder = processEngine.getRepositoryService().createDeployment();
        builder.addClasspathResource("bpmn/test1.bpmn");
        builder.addClasspathResource("bpmn/test1.png");
        Deployment deployment = builder.deploy();
        System.out.println(deployment.getId());
    }
}
