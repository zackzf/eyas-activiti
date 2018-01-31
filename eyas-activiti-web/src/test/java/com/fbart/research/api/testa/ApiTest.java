package com.fbart.research.api.testa;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        builder.addClasspathResource("leave.bpmn");
        builder.addClasspathResource("leave.png");
        Deployment deployment = builder.deploy();
        System.out.println(deployment.getId());
    }

    @Test
    public void test2() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        processDefinitionQuery.processDefinitionKey("leave");
        processDefinitionQuery.orderByProcessDefinitionVersion().desc();
        processDefinitionQuery.listPage(0, 10);
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.list();
        for (ProcessDefinition pd : processDefinitionList) {
            System.out.println(pd.getId());
        }
    }

    @Test
    public void test3() {
        String processDefinitionId = "leave:8:904";
//        String processDefinitionId = "leave:6:504";
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId);
        System.out.println(processInstance.getId());
    }

    @Test
    public void test4() {
        TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery();
        String taskAssignee = "张三";
        taskQuery.taskAssignee(taskAssignee);
        List<Task> taskList = taskQuery.list();
        for (Task task : taskList) {
            System.out.println(task.getId() + "  " + task.getName());
        }
    }

    @Test
    public void test5() {
        String taskId = "1202";
        processEngine.getTaskService().complete(taskId);
    }

    /**
     * 查询历史流程实例
     * act_hi_procinst
     */
    @Test
    public void test6() {
        HistoryService historyService = processEngine.getHistoryService();
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();
        List<HistoricProcessInstance> historicProcessInstanceList = query.list();
        for (HistoricProcessInstance instance : historicProcessInstanceList) {
            System.out.println(instance.getId());
        }
    }

    /**
     * 查询历史活动数据，一个流程实例的每一步是一个activity
     * act_hi_actinst
     */
    @Test
    public void test7() {
        HistoryService historyService = processEngine.getHistoryService();
        HistoricActivityInstanceQuery query = historyService.createHistoricActivityInstanceQuery();
        List<HistoricActivityInstance> historicActivityInstanceList = query.list();
        for (HistoricActivityInstance instance : historicActivityInstanceList) {
            System.out.println(instance.getActivityId() + "  " + instance.getActivityName() + "  " + instance.getActivityType());
        }
    }

    /**
     * 查询历史任务数据
     * act_hi_taskinst
     */
    @Test
    public void test8() {
        HistoryService historyService = processEngine.getHistoryService();
        HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();
        //processInstanceId是按字符串排序
//        query.orderByProcessInstanceId().desc();
        query.orderByHistoricTaskInstanceEndTime().desc();
        List<HistoricTaskInstance> historicTaskInstanceList = query.list();
        for (HistoricTaskInstance instance : historicTaskInstanceList) {
            System.out.println(instance.getAssignee() + "  " + instance.getName() + "  " + instance.getEndTime() + "  " + instance.getProcessInstanceId());
        }
    }

    /**
     * 设置流程变量
     * 在启动流程实例时设置
     * act_ru_variable
     */
    @Test
    public void test9() {
        String processInstanceId = "leave:8:904";
        Map<String, Object> variables = new HashMap<>();
        variables.put("key1", "v1");
        variables.put("key2", 100);
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processInstanceId, variables);
        System.out.println(processInstance.getId());
    }

    /**
     * 设置流程变量
     * 在办理任务时设置
     * act_ru_variable
     */
    @Test
    public void test10() {
        String taskId = "704";
        Map<String, Object> variables = new HashMap<>();
        variables.put("key3", "v3");
        variables.put("key4", 400);
        TaskService taskService = processEngine.getTaskService();
        taskService.complete(taskId, variables);
    }
}
