package uk.ac.sheffield.com1003.cafe.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.io.FileWriter;
import java.io.IOException;

@Aspect
public class MethodCallTrace {
    public FileWriter writer;
    public int index = 0;

    public MethodCallTrace() throws IOException {
        writer = new FileWriter("log.txt");
    }

    @Pointcut("execution(* uk.ac.sheffield.com1003.cafe.*.*(..)) || execution(* uk.ac.sheffield.com1003.cafe.*.*.*(..))")
    public void recordExecution() {
    }

//    @Pointcut("within(test.java.uk.ac.sheffield.com1003.cafe.*)")
//    public void testCaseExecution() {
//    }

    @Before("recordExecution()")
    public void logMethodExecution(JoinPoint joinPoint) throws Throwable {
        String methodName = String.join(".", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        writer.write(methodName + "\n");
        writer.flush();
    }

//    @After("testCaseExecution()")
//    public void separateMethods() throws Throwable {
//        writer.write(index + " ***** \n");
//        index += 1;
//        writer.flush();
//    }
}
