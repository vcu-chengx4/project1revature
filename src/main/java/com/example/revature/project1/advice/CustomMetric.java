package com.example.revature.project1.advice;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.aop.TimedAspect;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CustomMetric {
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
}