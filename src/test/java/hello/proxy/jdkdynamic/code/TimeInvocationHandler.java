package hello.proxy.jdkdynamic.code;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeInvocationHandler implements InvocationHandler {

    private final Object target;

    public TimeInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("TimeProxy 실행");

        final long startTime = System.currentTimeMillis();

        final Object result = method.invoke(target, args);
        final long endTime = System.currentTimeMillis();

        final long resultTIme = endTime - startTime;

        log.info("TimeProxy 종료, resultTime={}",resultTIme);

        return result;
    }
}
