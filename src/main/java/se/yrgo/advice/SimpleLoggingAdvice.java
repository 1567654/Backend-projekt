package se.yrgo.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SimpleLoggingAdvice {

    private static final Logger logger = LoggerFactory.getLogger(SimpleLoggingAdvice.class);

    @Before("execution(public * se.yrgo..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Now about to call the {} method", joinPoint.getSignature().getName());
    }
}