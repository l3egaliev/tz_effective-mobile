package ru.loggable.aspects;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Aspect
@Component
public class LoggableAspect {
    private final Logger logger = LoggerFactory.getLogger(LoggableAspect.class);

    @Around("execution(* ru.rakhim.banking_system.service..*(..))")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        Object[] args = joinPoint.getArgs();
        Instant startTime = Instant.now();
        logger.info("Method {} called with arguments: {}", className + "." + methodName, Arrays.toString(args));
        logger.info("Method {} returned: {}", className + "." + methodName, joinPoint.proceed());
        Instant endTime = Instant.now();
        Duration duration = Duration.between(startTime, endTime);
        logger.info("Method {} executed in: {}ms", className + "." + methodName, duration.toMillis());
        return joinPoint.proceed();
    }
}