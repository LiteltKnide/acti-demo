package com.example.actidemo.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @author hujt49
 * @Description
 * @create 2020-09-21 11:18
 */
public class GroupTask3Listener implements TaskListener {
    private static final long serialVersionUID = -4617807354339318473L;

    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.addCandidateUser("王总");
        delegateTask.addCandidateUser("李总");
        delegateTask.addCandidateUser("钱总");
    }
}
