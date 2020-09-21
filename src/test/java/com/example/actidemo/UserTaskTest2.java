package com.example.actidemo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hujt49
 * @Description
 * @create 2020-09-21 10:50
 */
public class UserTaskTest2 {

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
    public void testUserTask2() {
        // 流程启动
        String assignee = "王总";
        Map<String, Object> var = new HashMap<>();
        var.put("userId", assignee);
        ProcessInstance userTaskTest2 = runtimeService
                .startProcessInstanceByKey("UserTaskTest", var);
        System.err.println(userTaskTest2.getId());

        // 查看个人任务
        List<Task> taskList = taskService
                .createTaskQuery()
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
