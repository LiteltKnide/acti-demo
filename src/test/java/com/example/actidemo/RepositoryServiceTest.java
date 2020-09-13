package com.example.actidemo;

import org.activiti.engine.*;
import org.activiti.engine.repository.DeploymentBuilder;
import org.junit.Test;

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
        DeploymentBuilder deployment = repositoryService.createDeployment();

        repositoryService.deleteDeployment("deploymentId");
    }
}
