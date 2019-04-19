package com.smile.sentineldemo.listenner;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.apollo.ApolloDataSource;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.util.List;

/**
 * @author ：Smile(wangyajun)
 * @date ：Created in 2019/4/18 17:05
 * @description：
 */
public class ApolloDataSourceListenner implements ApplicationListener<ApplicationReadyEvent> {


    private String namespaceName;

    private String flowRulesKey;

    private String defaultFlowRuleValue;

    private String defaultDegradeRuleValue;

    private String degradeRulesKey;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        namespaceName = applicationReadyEvent.getApplicationContext().getEnvironment().getProperty("namespace.name");
        flowRulesKey = applicationReadyEvent.getApplicationContext().getEnvironment().getProperty("flow.rules.key");
        defaultFlowRuleValue = applicationReadyEvent.getApplicationContext().getEnvironment().getProperty("default.flow.rule.value");
        degradeRulesKey = applicationReadyEvent.getApplicationContext().getEnvironment().getProperty("degrade.rules.key");
        defaultDegradeRuleValue = applicationReadyEvent.getApplicationContext().getEnvironment().getProperty("default.degrade.rule.value");

        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new ApolloDataSource<>(
                namespaceName,
                flowRulesKey,
                defaultFlowRuleValue,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                }));


        ReadableDataSource<String, List<DegradeRule>> degradeRuleDataSource = new ApolloDataSource<>(
                namespaceName,
                degradeRulesKey,
                defaultDegradeRuleValue,
                source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {
                }));

        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
        DegradeRuleManager.register2Property(degradeRuleDataSource.getProperty());

    }

}
