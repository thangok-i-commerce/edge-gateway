package thangok.icommerce.edgegateway.aop.io;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class IOAspect {
    @Around("@annotation(LogIO)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        // input
        StringBuilder iSb = new StringBuilder();
        iSb.append(String.format("Enter method [%s] with parameters:(", joinPoint.getSignature()));
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            iSb.append(arg).append(",");
        }
        if (args.length > 0) {
            iSb.deleteCharAt(iSb.length() - 1);
        }
        iSb.append(")");
        log.info(iSb.toString());

        Object result = joinPoint.proceed();

        // output
        StringBuilder oSb = new StringBuilder();
        oSb.append(String.format("Exit method [%s] with parameters:(", joinPoint.getSignature()));
        oSb.append(result.toString());
        oSb.append(")");
        log.info(oSb.toString());

        return result;
    }
}
