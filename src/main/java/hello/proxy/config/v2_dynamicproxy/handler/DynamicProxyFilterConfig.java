package hello.proxy.config.v2_dynamicproxy.handler;

import hello.proxy.app.v1.OrderControllerV2;
import hello.proxy.app.v1.OrderControllerV1Impl;
import hello.proxy.app.v1.OrderRepositoryV2;
import hello.proxy.app.v1.OrderRepositoryV1Impl;
import hello.proxy.app.v1.OrderServiceV2;
import hello.proxy.app.v1.OrderServiceV1Impl;
import hello.proxy.trace.logtrace.LogTrace;
import java.lang.reflect.Proxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamicProxyFilterConfig {

    private static final String[] PATTERNS = {"request*", "order*", "save*"};

    @Bean
    public OrderControllerV2 orderControllerV1(LogTrace logTrace) {
        final OrderControllerV2 orderControllerV1 = new OrderControllerV1Impl(orderServiceV1(logTrace));

        OrderControllerV2 proxy = (OrderControllerV2) Proxy.newProxyInstance(orderControllerV1.getClass().getClassLoader(),
            new Class[]{OrderControllerV2.class},
            new LogTraceFilterHandler(orderControllerV1, logTrace, PATTERNS));

        return proxy;
    }

    @Bean
    public OrderServiceV2 orderServiceV1(LogTrace logTrace) {
        final OrderServiceV2 orderServiceV1 = new OrderServiceV1Impl(orderRepositoryV1(logTrace));

        OrderServiceV2 proxy = (OrderServiceV2) Proxy
            .newProxyInstance(orderServiceV1.getClass().getClassLoader(),
                new Class[]{OrderServiceV2.class},
                new LogTraceFilterHandler(orderServiceV1, logTrace, PATTERNS));

        return proxy;
    }

    @Bean
    public OrderRepositoryV2 orderRepositoryV1(LogTrace logTrace) {
        OrderRepositoryV2 orderRepositoryV1 = new OrderRepositoryV1Impl();

        final OrderRepositoryV2 proxy = (OrderRepositoryV2) Proxy.newProxyInstance(orderRepositoryV1.getClass().getClassLoader(),
            new Class[]{OrderRepositoryV2.class},
            new LogTraceFilterHandler(orderRepositoryV1, logTrace, PATTERNS));

        return proxy;
    }
}
