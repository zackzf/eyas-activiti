package com.fbart.research.web.service;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/applicationContext.xml")
public class ActivitiTest {
    @Autowired
    private ProcessEngine processEngine;

    @Test
    public void test1() {
        DeploymentBuilder builder = processEngine.getRepositoryService().createDeployment();
        builder.addClasspathResource("bpmn/test1.bpmn");
        builder.addClasspathResource("bpmn/test1.png");
        Deployment deployment = builder.deploy();
        System.out.println(deployment.getId());
    }

    @Test
    public void test2() {
        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("test1");
        System.out.println(processInstance.getId());
    }

    @Test
    public void test3() {
        TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery();
        taskQuery.processInstanceId("301");
        List<Task> list = taskQuery.list();
        for (Task task : list) {
            System.out.println(task.getName());
            System.out.println(task.getId());
        }

    }

    @Test
    public void test4() {
        String taskId = "402";
        processEngine.getTaskService().complete(taskId);
    }
}
