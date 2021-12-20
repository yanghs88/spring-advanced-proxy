package hello.proxy.cglib.code;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

@Slf4j
public class TimeMethodInterceptor implements MethodInterceptor {

    private final Object target;

    public TimeMethodInterceptor(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy)
        throws Throwable {

        log.info("TimeProxy 실행");
        final long startTime = System.currentTimeMillis();

        final Object result = methodProxy.invoke(target, args);

        final long endTime = System.currentTimeMillis();
        final long resultTIme = endTime - startTime;
        log.info("TimeProxy 종료, resultTime={}",resultTIme);

        return result;

    }
}
