package com.lyn.practice.service.spring.cloud.server.aop;


import com.lyn.practice.service.spring.cloud.server.annotation.LimitVisit;
import com.lyn.practice.service.spring.cloud.server.annotation.TimeoutServiceBreaker;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.lang.reflect.Method;
import java.util.concurrent.*;

@Component
@Aspect
public class SayAspect {

    private static final ExecutorService sayThreadPool = Executors.newCachedThreadPool();

    private volatile Semaphore semaphore = null;

    @Pointcut(value = "@annotation(com.lyn.practice.service.spring.cloud.server.annotation.TimeoutServiceBreaker)")
    public void timeoutServiceBreakerPointCut() {
    }

    @Pointcut(value = "@annotation(com.lyn.practice.service.spring.cloud.server.annotation.LimitVisit)")
    public void limitVisitPointCut() {
    }


    @Around(value = ("limitVisitPointCut()||timeoutServiceBreakerPointCut()"))
    public Object limitVisitAround(ProceedingJoinPoint joinPoint) {
        Method method = this.getMethod(joinPoint);
        Object returnValue = null;
        if (method != null && method.isAnnotationPresent(LimitVisit.class)) {
            LimitVisit limitVisit = method.getAnnotation(LimitVisit.class);
            if (semaphore == null) {
                semaphore = new Semaphore(limitVisit.value());
            }

            try {
                if(semaphore.tryAcquire()){
                    /*try {
                        Thread.sleep(100000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    returnValue =  this.timeOutExecute(joinPoint);
                    returnValue+="//"+semaphore.availablePermits();
                }else{
                    returnValue =  this.handleErr("限流->"+semaphore.getQueueLength());
                }

            } finally {
                semaphore.release();
            }
        } else {
            returnValue = timeOutExecute(joinPoint);

        }
        return returnValue;
    }

    //@Around(value = "execution(* com.lyn.practice.service.spring.cloud.server.controller.SayController.say1(..))&& @annotation(timeoutServiceBreaker)")
    public Object around(ProceedingJoinPoint joinPoint, TimeoutServiceBreaker timeoutServiceBreaker) {
        Future<Object> future = sayThreadPool.submit(new Callable<Object>() {

            public Object call() throws Exception {
                try {
                    return joinPoint.proceed(joinPoint.getArgs());
                } catch (Throwable throwable) {

                }
                return null;
            }

        });

        Object result;
        try {
            result = future.get(timeoutServiceBreaker.timeout(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            future.cancel(true);
            result = this.handleErr("超时");
        }

        return result;
    }


    public String handleErr(String errorMesage) {
        return "Error:"+errorMesage;
    }

    @PreDestroy
    public void destroy() {
        sayThreadPool.shutdown();
    }


    private Method getMethod(ProceedingJoinPoint joinPoint) {
        if (joinPoint instanceof MethodInvocationProceedingJoinPoint) {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            return methodSignature.getMethod();
        }
        //signature.getMethod();
        return null;
    }

    private Object timeOutExecute(ProceedingJoinPoint joinPoint,Object ...message) {
        Method method = this.getMethod(joinPoint);
        Object returnValue = null;
        if (method != null && method.isAnnotationPresent(TimeoutServiceBreaker.class)) {
            TimeoutServiceBreaker timeoutServiceBreaker = method.getAnnotation(TimeoutServiceBreaker.class);
            Future<Object> future = sayThreadPool.submit(() -> {
                Object ret = null;
                try {
                    ret = joinPoint.proceed();
                } catch (Throwable e) {
                }
                return ret;
            });

            try {
                returnValue = future.get(timeoutServiceBreaker.timeout(), TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                System.out.printf("超时:" + timeoutServiceBreaker.timeout() + "毫秒");
                future.cancel(true);
                returnValue = this.handleErr("超时");
            }

        } else {
            try {
                returnValue = joinPoint.proceed();
            } catch (Throwable e) {

                returnValue = this.handleErr(e.getMessage());
            }
        }

        return returnValue;
    }
}
