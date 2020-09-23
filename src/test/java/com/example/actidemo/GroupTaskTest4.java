package com.example.actidemo;

import org.activiti.engine.*;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author hujt49
 * @Description
 * @create 2020-09-21 10:50
 */
public class GroupTaskTest4 {

    private ProcessEngine processEngine;
    private RuntimeService runtimeService;
    private TaskService taskService;
    private IdentityService identityService;


    @Before
    public void before() {
        processEngine = ProcessEngines.getDefaultProcessEngine();
        runtimeService = processEngine.getRuntimeService();
        taskService = processEngine.getTaskService();
        identityService = processEngine.getIdentityService();
    }

    @Test
    public void testSetGroupIdentity() {
        identityService.saveGroup(new GroupEntity("部门经理"));
        identityService.saveGroup(new GroupEntity("总经理"));
        identityService.saveUser(new UserEntity("胡总"));
        identityService.saveUser(new UserEntity("谢总"));
        identityService.saveUser(new UserEntity("马总"));
        identityService.createMembership("胡总", "部门经理");
        identityService.createMembership("谢总", "部门经理");
        identityService.createMembership("马总", "总经理");
    }


    @Test
    public void testGroupTask3() {
        // 流程启动
        ProcessInstance userTaskTest1 = runtimeService
                .startProcessInstanceByKey("GroupTaskTest");
        System.err.println(userTaskTest1.getId());
    }

    @Test
    public void testUserTaskList() {
        // 查看个人任务
        String assignee = "胡总";
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
        // 查看组任务  胡总和谢总是部门经理，能查询到任务  而马总是总经理，查询不到任务
        String assignee = "胡总";
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
        String taskId = "225004";
        List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(taskId);
        identityLinksForTask.forEach(identityLink -> {
            System.err.println(identityLink.getGroupId());
            System.err.println(identityLink.getUserId());
        });

    }

    @Test
    public void tsetCliamTask() {
        // 认领任务
        String taskId = "225004";
        taskService.claim(taskId, "胡总");
    }

    @Test
    public void testBackGroupTask() {
        // 回到组任务状态  assignee为null
        taskService.setAssignee("225004", null);
    }

    @Test
    public void testAddOrDeleteCandidateUser() {
        taskService.addCandidateUser("225004", "钱总");

        taskService.deleteCandidateUser("225004", "王总");
    }

    @Test
    public void testCompleted() {
        taskService.complete("225004");
    }
}
