package com.example.actidemo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author hujt49
 * @Description
 * @create 2020-09-21 10:50
 */
public class UserTaskTest1 {

    private ProcessEngine processEngine;
    private RuntimeService runtimeService;
    private TaskService taskService;


    @Before
    public void before() {
        processEngine = ProcessEngines.getDefaultProcessEngine();
        runtimeService = processEngine.getRuntimeService();
        taskService = processEngine.getTaskService();
    }

    @Test
    public void testUserTask1() {
        // 流程启动
        ProcessInstance userTaskTest1 = runtimeService
                .startProcessInstanceByKey("UserTaskTest");
        System.err.println(userTaskTest1.getId());

        // 查看个人任务
        String assignee = "王总";
        List<Task> taskList = taskService
                .createTaskQuery()
                .processInstanceId(userTaskTest1.getId())
                .taskAssignee(assignee)
                .list();

        taskList.forEach(task -> {
            System.err.println("任务" + task.getId() + "执行人:" + task.getAssignee());
            // 完成任务
            taskService.complete(task.getId());
            System.err.println("任务" + task.getId() + "完成");
        });

    }
}
