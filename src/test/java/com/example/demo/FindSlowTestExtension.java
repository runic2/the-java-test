package com.example.demo;

import java.lang.reflect.Method;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class FindSlowTestExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
	
//	private static final long THRESHOLD = 1000L;

//	THRESHOLD의 값을 상황마다 변경하고 싶은 경우?
	private long THRESHOLD;
	public FindSlowTestExtension(long THRESHOLD) {
		// TODO Auto-generated constructor stub
		this.THRESHOLD = THRESHOLD;
	}
	
	@Override
	public void beforeTestExecution(ExtensionContext context) throws Exception {
		// TODO Auto-generated method stub
		ExtensionContext.Store store = getStore(context);
		store.put("START_TIME", System.currentTimeMillis());
	}
	
	@Override
	public void afterTestExecution(ExtensionContext context) throws Exception {
		// TODO Auto-generated method stub
		Method requiredTestMethod = context.getRequiredTestMethod();
		SlowTest annotation = requiredTestMethod.getAnnotation(SlowTest.class);		
		//Test Method의 Annotation 객체를 가져옴
		String testMethodName = requiredTestMethod.getName();
		//... 이름을 가져옴
		
		//store => 확장 기능이 데이터를 저장하고 검색하는 방법을 제공합니다.
		ExtensionContext.Store store = getStore(context);
		long start_time = store.remove("START_TIME", long.class);		
		long duration = System.currentTimeMillis() - start_time;
		if (duration > THRESHOLD && annotation == null) {
			System.out.printf("Please consider mark method [%s] with @SlowTest. \n", testMethodName);
		}
	}
	
	//store => 확장 기능이 데이터를 저장하고 검색하는 방법을 제공합니다.
	private ExtensionContext.Store getStore(ExtensionContext context) {
		String testClassName = context.getRequiredTestClass().getName();
		String testMethodName = context.getRequiredTestMethod().getName();
		ExtensionContext.Store store = context.getStore(ExtensionContext.Namespace.create(testClassName, testMethodName));
		return store;
	}
}
