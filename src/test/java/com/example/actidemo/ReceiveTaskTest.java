package com.example.actidemo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hujt49
 * @Description  接受活动
 * @create 2020-09-21 9:26
 */
public class ReceiveTaskTest {

    private ProcessEngine processEngine;

    @Before
    public void before() {
        processEngine = ProcessEngines.getDefaultProcessEngine();
    }

    @Test
    public void testStartProcess() {
        ProcessInstance processInstance = processEngine.getRuntimeService()
                .startProcessInstanceByKey("receiveTask");
        System.err.println(processInstance.getId());
    }

    @Test
    public void testReceiveTask() {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        String pid = "172501";
        Execution execution = runtimeService
                .createExecutionQuery()
                .processInstanceId(pid)
                .singleResult();
        System.err.println(execution.getId());
        System.err.println(execution.getActivityId());


        Map<String, Object> var1 = new HashMap<>();
        var1.put("money", 10000);
        runtimeService.signal(execution.getId(), var1);
        System.err.println("当天销售额汇总完成");

        Execution execution1 = runtimeService.createExecutionQuery()
                .processInstanceId(pid)
                .singleResult();
        System.err.println(execution1.getId());
        System.err.println(execution1.getActivityId());

        Integer money = (Integer) runtimeService.getVariable(execution1.getId(), "money");
        System.err.println("老板，今天赚了" + money);

        Map<String, Object> var2 = new HashMap<>();
        var2.put("Text Boss", true);
        runtimeService.signal(execution1.getId(), var2);
        System.err.println("向老板发送短信完成");

        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(pid)
                .singleResult();
        if (processInstance == null) {
            System.err.println("流程正常结束");
        }
    }


}
