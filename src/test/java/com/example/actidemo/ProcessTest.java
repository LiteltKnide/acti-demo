package com.example.actidemo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * @author hujt49
 * @Description
 * @create 2020-09-14 15:21
 */
public class ProcessTest {


    @Test
    public void testDeploy() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("static/Vacation.zip");
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("请假申请流程")
                .addZipInputStream(zipInputStream)
                .deploy();
        System.err.println(deployment.getId());
    }

    @Test
    public void testStartProcess() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessInstance processInstance = processEngine.getRuntimeService()
                // .startProcessInstanceByKey("vacation");   使用流程key启动流程 默认使用最高版本流程
                .startProcessInstanceById("vacation:2:5004");
        System.err.println(processInstance.getId());
        System.err.println(processInstance.getDeploymentId());
        System.err.println(processInstance.getProcessDefinitionId());
        System.err.println(processInstance.getActivityId());
    }

    @Test
    public void testTaskList() {
        String assginee = "张三";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> taskList = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee(assginee)// 任务办理人
                .orderByTaskCreateTime().asc() // 任务创建时间正序
                .list();
        taskList.forEach(System.err::println);
    }

    @Test
    public void testCompleted() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("12502");// 任务id
    }

    @Test
    public void testProcessStatus() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessInstance processInstance = processEngine.getRuntimeService()
                .createProcessInstanceQuery()
                .processDefinitionId("vacation:2:5004")
                .singleResult();
        if (processInstance != null) {
            System.err.println("流程正在执行：" + processInstance.getActivityId());
        } else {
            System.err.println("流程已结束!");
        }
    }
}
