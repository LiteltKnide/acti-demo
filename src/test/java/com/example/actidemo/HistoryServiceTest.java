package com.example.actidemo;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author hujt49
 * @Description   历史记录
 * @create 2020-09-15 13:59
 */
public class HistoryServiceTest {

    ProcessEngine processEngine;

    @Before
    public void before() {
        processEngine = ProcessEngines.getDefaultProcessEngine();
    }

    /**
     * 查看历史流程实例
     */
    @Test
    public void testQueryHistoryProcessInstance() {
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery()
                .processDefinitionKey("vacation2")
                .orderByProcessInstanceStartTime().desc()
                .list();
        historicProcessInstances.forEach(historicProcessInstance -> {
            System.err.println("id = " + historicProcessInstance.getId());
            System.err.println("pdid = " + historicProcessInstance.getProcessDefinitionId());
            System.err.println("did" + historicProcessInstance.getDeploymentId());
            System.err.println(historicProcessInstance.getStartTime());
            System.err.println(historicProcessInstance.getEndTime());
            System.err.println("cost" + historicProcessInstance.getDurationInMillis() + "ms");
            System.err.println("==============================");
        });
    }

    /**
     * 历史活动查看（某一次流程的执行经过多少步）
     */
    @Test
    public void testHistoryActivityInstance() {
        List<HistoricActivityInstance> historicActivityInstances = processEngine.getHistoryService()
                .createHistoricActivityInstanceQuery()
                .processInstanceId("27501")
                .orderByHistoricActivityInstanceEndTime().asc()
                .list();
        historicActivityInstances.forEach(historicActivityInstance -> {
            System.err.println("activitiId:" + historicActivityInstance.getActivityId());
            System.err.println("name:" + historicActivityInstance.getActivityName());
            System.err.println("type:" + historicActivityInstance.getActivityType());
            System.err.println("pid:" + historicActivityInstance.getProcessInstanceId());
            System.err.println("assignee:" + historicActivityInstance.getAssignee());
            System.err.println("startTime:" + historicActivityInstance.getStartTime());
            System.err.println("endTime:" + historicActivityInstance.getEndTime());
            System.err.println("duration:" + historicActivityInstance.getDurationInMillis());
            System.err.println("======================================================");
        });
    }

    /**
     * 历史任务查看（某一次流程执行经历多少任务节点）
     */
    @Test
    public void testQueryHistoryTask() {
        List<HistoricTaskInstance> historicTaskInstances = processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .processInstanceId("27501")
                .orderByHistoricTaskInstanceEndTime().asc()
                .list();
        historicTaskInstances.forEach(historicTaskInstance -> {
            System.err.println("taskId:" + historicTaskInstance.getId());
            System.err.println("name:" + historicTaskInstance.getName());
            System.err.println("assignee:" + historicTaskInstance.getAssignee());
            System.err.println("pdid:" + historicTaskInstance.getProcessDefinitionId());
            System.err.println("piid:" + historicTaskInstance.getProcessInstanceId());
            System.err.println("startTime:" + historicTaskInstance.getStartTime());
            System.err.println("endTime:" + historicTaskInstance.getEndTime());
            System.err.println("duration:" + historicTaskInstance.getDurationInMillis());
            System.err.println("=======================================================");
        });
    }

    /**
     * 某一次流程执行中设置的流程变量
     */
    @Test
    public void testHistoryVariables() {
        List<HistoricVariableInstance> historicVariableInstances = processEngine.getHistoryService()
                .createHistoricVariableInstanceQuery()
                .processInstanceId("27501")
                .list();
        historicVariableInstances.forEach(historicVariableInstance -> {
            System.err.println("piid:" + historicVariableInstance.getProcessInstanceId());
            System.err.println("name:" + historicVariableInstance.getVariableName());
            System.err.println("value:" + historicVariableInstance.getValue());
            System.err.println("taskId:" + historicVariableInstance.getTaskId());
            System.err.println("type:" + historicVariableInstance.getVariableTypeName());
            System.err.println("=======================================");
        });
    }
}
