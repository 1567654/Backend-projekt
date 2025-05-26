package se.yrgo.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceTimingAdvice {
    public Object performanceTimingMeasurement(ProceedingJoinPoint jp) throws Throwable {
        long start = System.nanoTime();
        Object result = jp.proceed();
        long end = System.nanoTime();

        double duration = (end - start) / 1_000_000.0;

        System.out.printf("Time taken for the method %s from the class %s took %.4fms%n",
                jp.getSignature().getName(),
                jp.getSignature().getDeclaringTypeName(),
                duration);

        return result;
    }
}