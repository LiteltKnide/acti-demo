package com.example.actidemo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hujt49
 * @Description  连线
 * @create 2020-09-17 11:15
 */
public class FlowTest {

    private ProcessEngine processEngine;

    @Before
    public void before() {
        processEngine = ProcessEngines.getDefaultProcessEngine();
    }

    /**
     * 根据设计器生成的model部署流程
     * @throws Exception
     */
    @Test
    public void testDeployByModel() throws Exception {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        String modelId = "70001";
        Model model = repositoryService.getModel(modelId);
        byte[] source = repositoryService.getModelEditorSource(model.getId());
        if (source == null) {
            System.err.println("model：" + modelId + "不存在");
            return;
        }
        JsonNode jsonNode = new ObjectMapper().readTree(source);
        BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(jsonNode);

        String processName = model.getName()+".bpmn";
        byte[] bytes = new BpmnXMLConverter().convertToXML(bpmnModel);
        // 部署流程
        Deployment deployment = repositoryService
                .createDeployment().name(model.getName())
                .addString(processName, new String(bytes,"UTF-8"))
                .deploy();
        System.err.println(deployment.getId());
    }

    /**
     * 启动流程
     */
    @Test
    public void testStartProcess() {
        ProcessInstance vacation = processEngine.getRuntimeService()
                .startProcessInstanceByKey("Vacation");
        System.err.println(vacation.getId());
        System.err.println(vacation.getProcessDefinitionId());
        System.err.println(vacation.getName());
        System.err.println(vacation.getDeploymentId());
    }

    /**
     * 完成任务
     */
    @Test
    public void testCompleted() {
        String taskId = "90003";
        Map<String, Object> variables = new HashMap<>();
        // variables.put("message", "不重要");
        processEngine.getTaskService()
                .complete(taskId, variables);
    }

}
