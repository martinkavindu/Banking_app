package mutuku.coke.bankingapp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger =
            LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* mutuku.coke.bankingapp.service.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint)
            throws Throwable {

        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long executionTime =
                System.currentTimeMillis() - start;

        logger.info(
                "Executed {} in {} ms",
                joinPoint.getSignature(),
                executionTime
        );

        return result;
    }
    @Before("execution(* mutuku.coke.bankingapp.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {

        System.out.println(
                "Calling: " +
                        joinPoint.getSignature().getName()
        );
    }
}
