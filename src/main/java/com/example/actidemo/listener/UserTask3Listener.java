package com.example.actidemo.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @author hujt49
 * @Description
 * @create 2020-09-21 11:18
 */
public class UserTask3Listener implements TaskListener {
    private static final long serialVersionUID = -4617807354339318473L;

    @Override
    public void notify(DelegateTask delegateTask) {
        String assignee = "王总";
        delegateTask.setAssignee(assignee);
    }
}
