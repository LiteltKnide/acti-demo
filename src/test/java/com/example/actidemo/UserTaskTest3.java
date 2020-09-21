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
public class UserTaskTest3 {

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
    public void testUserTask3() {
        // 流程启动
        ProcessInstance userTaskTest3 = runtimeService
                .startProcessInstanceByKey("UserTaskTest");
        System.err.println(userTaskTest3.getId());

        // 查看个人任务
        String assignee = "王总";
        List<Task> taskList = taskService
                .createTaskQuery()
                .taskAssignee(assignee)
                .list();

        taskList.forEach(task -> {
            System.err.println("任务" + task.getId() + "执行人:" + task.getAssignee());
            // 可以分配个人任务从一个人到另一个人（认领任务）
            // taskService.setAssignee(task.getId(), "张总");
            // 完成任务
            taskService.complete(task.getId());
            System.err.println("任务" + task.getId() + "完成");
        });
    }
}
