package com.example.actidemo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;

import java.io.*;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * @Auther ASUS
 * @CreateTime 2020/9/13
 * @Description
 */
public class RepositoryServiceTest {

    @Test
    public void testDeploy() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .name("请假申请流程")// 添加部署显示名称
                .addClasspathResource("static/Vacation.bpmn")// 添加流程定义的规则文件
                .addClasspathResource("static/Vacation.png")// 添加流程定义的图片文件
                .deploy();
        System.err.println(deployment.getId());
    }

    @Test
    public void testDeploy_2() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("static/Vacation.zip");
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("请假流程")
                .addZipInputStream(zipInputStream)
                .deploy();
        System.err.println(deployment.getId());
    }

    @Test
    public void testProcessDefinition() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<ProcessDefinition> processDefinitions = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                // 添加查询条件
                // .processDefinitionId("")
                // .processDefinitionKey("")
                // .processDefinitionName("")
                // 排序
                .orderByProcessDefinitionId().asc()
                // 结果集
                // .listPage(1, 10);
                .list();
        processDefinitions.forEach(System.err::println);
    }

    @Test
    public void testDeleteDeploy() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                // 普通删除： 只会删除流程部署和定义  如果有正在执行的流程，会抛异常
                // .deleteDeployment("1");
                // 联级删除  会删除跟单签流程相关的所有信息，也包括历史信息
                .deleteDeployment("1", true);
    }

    @Test
    public void testViewImage() throws Exception {
        String deploymentId = "170004";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<String> resourceNames = processEngine.getRepositoryService()
                .getDeploymentResourceNames(deploymentId);
        resourceNames.forEach(resourceName -> {
            System.err.println(resourceName);
            File file = new File("E:\\MyWork\\WorkSpace\\IDEASpace\\acti-demo\\src\\main\\resources\\static\\" + resourceName);
            InputStream inputStream = processEngine.getRepositoryService()
                    .getResourceAsStream(deploymentId, resourceName);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] bytes = new byte[1024];
                while (inputStream.read(bytes) > 0) {
                    fileOutputStream.write(bytes);
                }
                fileOutputStream.flush();
                inputStream.close();
                fileOutputStream.close();
            } catch (Exception e) {

            }
        });
    }
}
