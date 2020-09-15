package com.example.actidemo;

import com.example.actidemo.model.Person;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hujt49
 * @Description  流程变量
 * @create 2020-09-15 9:43
 */
public class VariablesTest {

    ProcessEngine processEngine;

    @Before
    public void before() {
        processEngine = ProcessEngines.getDefaultProcessEngine();
    }
    /**
     * 输入流加载资源文件的3种方式
     * this.getClass().getClassLoader().getResourceAsStream("static/Vacation2.bpmn");
     * 从classpath根目录下加载指定文件
     * this.getClass().getResourceAsStream("static/Vacation2.bpmn");
     * 从当前包下加载指定文件
     * this.getClass().getResourceAsStream("/static/Vacation2.bpmn")
     * 从classpath根目录下加载指定文件
     */
    @Test
    public void testDeploy() {
        InputStream inputStream = this.getClass().getResourceAsStream("/static/Vacation2.bpmn");
        InputStream inputStreampng = this.getClass().getResourceAsStream("/static/Vacation2.png");

        // ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("请假申请流程")
                // .addClasspathResource("static/Vacation2.bpmn")
                // .addClasspathResource("static/Vacation2.png")
                .addInputStream("static/Vacation2.bpmn", inputStream)
                .addInputStream("static/Vacation2.png", inputStreampng)
                .deploy();
        System.err.println(deployment.getId());
    }

    /**
     * 启动流程   可以设置流程变量
     */
    @Test
    public void testStartProcess() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("opTenant", "11121");
        // ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessInstance processInstance = processEngine.getRuntimeService()
                .startProcessInstanceByKey("vacation2", variables);
        System.err.println(processInstance.getProcessDefinitionId());
        System.err.println(processInstance.getActivityId());
        System.err.println(processInstance.getId());
    }

    /**
     *  完成流程，  可以设置流程变量
     */
    @Test
    public void testSetVariables() {
        Map<String, Object> variables = new HashMap<>();
        // 相同key值会覆盖
        variables.put("opTenant", "3333");
        // variables.put("comment", "申请假期");
        // variables.put("days", 5);
        //必须实现Serializable接口
        // Person person = new Person("小明", 12);
        // variables.put("with", person);
        // ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("30007", variables);
    }

    /**
     * 获取流程变量
     */
    @Test
    public void testGetVariables() {
        String taskId = "30007";
        // ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        String opTenant = (String) taskService.getVariable(taskId, "opTenant");
        String comment = (String) taskService.getVariable(taskId, "comment");
        Integer days = (Integer) taskService.getVariable(taskId, "days");
        Person with = taskService.getVariable(taskId, "with", Person.class);
        System.err.println("opTenant=" + opTenant);
        System.err.println("comment=" + comment);
        System.err.println("days=" + days);
        System.err.println("with=" + with);
        System.err.println("==============获取多个variables================");
        List<String> variableNames = new ArrayList<>();
        variableNames.add("opTenant");
        variableNames.add("comment");
        variableNames.add("days");
        variableNames.add("with");
        Map<String, Object> variables = taskService.getVariables(taskId, variableNames);
        variables.entrySet().forEach(entry -> System.err.println(entry.getKey() + "=" + entry.getValue()));
    }

    @Test
    public void testGetHistoryVariables() {
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery()
                .variableName("opTenant")
                .list();
        if (list != null) {
            list.forEach(System.err::println);
        }
    }

}
