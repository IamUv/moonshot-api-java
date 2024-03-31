package com.iamuv.moonshot.api.springboot;

import com.iamuv.moonshot.api.application.MoonshotApiApplication;
import com.iamuv.moonshot.api.infrastructure.client.JsonConverter;
import com.iamuv.moonshot.api.infrastructure.client.LogClient;
import com.iamuv.moonshot.api.springboot.client.JacksonJsonConverter;
import com.iamuv.moonshot.api.springboot.client.Sf4jLogClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Objects;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;


@Configuration
@EnableConfigurationProperties(MoonshotApiProperties.class)
public class MoonshotApiAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(MoonshotApiAutoConfiguration.class.getName());

    @Configuration
    @Conditional(MoonshotApiCondition.class)
    static class MoonshotApiApplicationConfiguration {

        @Bean
        @ConditionalOnMissingBean
        @Primary
        @DependsOn({"logClient", "converter"})
        MoonshotApiApplication moonshotApiApplication(JsonConverter converter, MoonshotApiProperties properties, LogClient logClient) {
            MoonshotApiApplication app = new MoonshotApiApplication(converter, properties.getApiKey(), logClient);
            logger.info("MoonshotApiApplication initialized with apiKey: {}", app.getToken());
            return app;
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(JsonConverter.class)
    static class JsonConverterConfiguration {
        @Bean
        @Primary
        @Scope(SCOPE_SINGLETON)
        @ConditionalOnMissingBean
        public JsonConverter jsonConverterConfiguration() {
            return new JacksonJsonConverter();
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(LogClient.class)
    static class LogClientConfiguration {
        @Bean
        @Primary
        @Scope(SCOPE_SINGLETON)
        @ConditionalOnMissingBean
        public LogClient logClientConfiguration() {
            return new Sf4jLogClient();
        }
    }

    @Configuration(proxyBeanMethods = false)
    static class MoonshotApiPropertiesConfiguration {
        @Bean
        @Primary
        @Scope(SCOPE_SINGLETON)
        @ConditionalOnMissingBean
        public MoonshotApiProperties moonshotApiProperties() {
            return new MoonshotApiProperties();
        }
    }

    // 自定义条件类，检查apiKey是否为空
    static class MoonshotApiCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            MoonshotApiProperties properties = Objects.requireNonNull(context.getBeanFactory()).getBean(MoonshotApiProperties.class);
            return properties.getApiKey() != null && !properties.getApiKey().isEmpty();
        }
    }
}
