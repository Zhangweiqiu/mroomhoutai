package com.example.demo.other;



import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/** 
 * 拦截器：记录用户操作日志，检查用户是否登录…… 
 * @author Zhangweiqiu 
 */  
@Aspect
@Order(-99) // 控制多个Aspect的执行顺序，越小越先执行
@Component
public class ControllerInterceptor{
	
	
	@Before("@annotation(test)")// 拦截被TestAnnotation注解的方法；如果你需要拦截指定package指定规则名称的方法
	//，可以使用表达式@Pointcut("@annotation(com.dlodlo.util.ActionControllerLog)")，具体百度一下资料一大堆
	public void beforeTest(JoinPoint point,TestAnnotation test) {
		System.out.println();
	}
	
	 @After("@annotation(test)")
	    public void afterTest(JoinPoint point, TestAnnotation test) {
	        System.out.println("afterTest:" + test.name());
	    }
	
	
}
