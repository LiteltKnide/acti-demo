package com.example.actidemo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * @Auther ASUS
 * @CreateTime 2020/9/13
 * @Description
 */
public class HelloworldTest {

    /**
     * 发布流程
     */
    @Test
    public void testDeploy() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("static/Helloworld.bpmn")
                .addClasspathResource("static/Helloworld.png")
                .deploy();
        System.err.println(deployment.getId());
        System.err.println(deployment.getName());
    }

    /**
     * 启动流程
     */
    @Test
    public void testProcess() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessInstance processInstance = processEngine.getRuntimeService()
                // .startProcessInstanceByKey("myProcess_1");// 使用流程定义的key启动流程，系统会默认使用最新版本（version）启动
                .startProcessInstanceById("myProcess_1:1:4");
        System.err.println(processInstance.getId());
    }

    /**
     * 查询个人任务
     */
    @Test
    public void testTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> taskList = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee("")
                .list();
        taskList.forEach(System.err::println);
    }

    /**
     * 完成任务
     */
    @Test
    public void testCompletedTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("7502");
    }
}
