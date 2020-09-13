package com.example.actidemo;

import org.activiti.engine.*;
import org.junit.Test;

/**
 * @Auther ASUS
 * @CreateTime 2020/9/13
 * @Description
 */
public class TableCreateTest {

    @Test
    public void createTable() {
        // 1.创建Activiti配置对象实例
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        // 2.设置数据库信息
        processEngineConfiguration.setJdbcUrl("jdbc:mysql://localhost:3306/activiti5?useUnicode=true&characterEncoding=utf-8");
        processEngineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
        processEngineConfiguration.setJdbcUsername("root");
        processEngineConfiguration.setJdbcPassword("root123456");
        // 3.设置数据库建表策略
        // DB_SCHEMA_UPDATE_FALSE: 如果不存在表就抛出异常
        // DB_SCHEMA_UPDATE_TRUE: 如果不存在就创建，存在就直接使用
        // DB_SCHEMA_UPDATE_CREATE_DROP: 每次都先删除表，再创建
        processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        System.err.println(processEngine);
    }

    @Test
    public void createTable_2() {
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        processEngineConfiguration.buildProcessEngine();
    }
}
