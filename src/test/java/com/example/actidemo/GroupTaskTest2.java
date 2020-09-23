package com.example.actidemo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
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
public class GroupTaskTest2 {

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
    public void testGroupTask2() {
        // 流程启动
        Map<String, Object> var = new HashMap<>();
        var.put("userIds", "王总,李总,张总");
        ProcessInstance userTaskTest1 = runtimeService
                .startProcessInstanceByKey("GroupTaskTest", var);
        System.err.println(userTaskTest1.getId());
    }

    @Test
    public void testUserTaskList() {
        // 查看个人任务
        String assignee = "王总";
        List<Task> taskList = taskService
                .createTaskQuery()
                .taskAssignee(assignee)
                .list();

        taskList.forEach(task -> {
            System.err.println("任务" + task.getId() + "执行人:" + task.getAssignee());
        });
    }


    @Test
    public void testGroupTaskList() {
        // 查看组任务  ？
        String assignee = "张总";
        List<Task> taskList = taskService
                .createTaskQuery()
                .taskCandidateUser(assignee)
                .list();

        taskList.forEach(task -> {
            System.err.println("任务" + task.getId() + "执行人:" + task.getAssignee());
        });

    }

    @Test
    public void testGetGroupUser() {
        String taskId = "212505";
        List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(taskId);
        identityLinksForTask.forEach(identityLink -> {
            System.err.println(identityLink.getGroupId());
            System.err.println(identityLink.getUserId());
        });

    }

    @Test
    public void tsetCliamTask() {
        // 认领任务
        String taskId = "212505";
        taskService.claim(taskId, "张总");
    }

    @Test
    public void testBackGroupTask() {
        // 回到组任务状态  assignee为null
        taskService.setAssignee("212505", null);
    }

    @Test
    public void testCompleted() {
        taskService.complete("212505");
    }
}
