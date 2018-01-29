package com.fbart.research.db.testa;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

public class DbTableBuildTest {
    @Test
    public void test1() {
        ProcessEngineConfiguration c = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        c.setJdbcDriver("com.mysql.jdbc.Driver");
        c.setJdbcUrl("jdbc:mysql://172.16.66.6:3306/activiti_513");
        c.setJdbcUsername("root");
        c.setJdbcPassword("root1234");
        c.setDatabaseSchemaUpdate("true");
        ProcessEngine processEngine = c.buildProcessEngine();
    }

    @Test
    public void test2() {
        String resource = "activiti-context.xml";
        String beanName = "processEngineConfiguration";
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(resource, beanName);
        ProcessEngine processEngine = configuration.buildProcessEngine();
    }

    @Test
    public void test3() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    }
}
