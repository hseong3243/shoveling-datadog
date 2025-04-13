package org.seong.shovelingdatadog.consumer

import io.opentelemetry.api.GlobalOpenTelemetry
import io.opentelemetry.api.trace.SpanKind
import io.opentelemetry.api.trace.StatusCode
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Aspect
@Component
class TraceConsumerAspect {

    @Pointcut("@annotation(org.springframework.amqp.rabbit.annotation.RabbitListener)")
    fun rabbitListener() {}

    @Around("rabbitListener()")
    fun traceConsumer(joinPoint: ProceedingJoinPoint): Any? {
        val methodName = joinPoint.signature.name
        val className = joinPoint.signature.declaringType.simpleName

        val tracer = GlobalOpenTelemetry.getTracer("event-consumer")
        val span = tracer.spanBuilder("${className}.${methodName}")
            .setSpanKind(SpanKind.CONSUMER)
            // datadog 문서 중 service override 참고
            // datadog은 service 이름을 override 한다.
            // service override를 끄기 위한 환경변수가 제공되나 event consumer 뿐만 아니라 queue 역시 base service 이름을 그대로 사용한다는 단점이 있다.
            // datadog 상에서 해당 환경변수는 `service` 속성이 항상 base service 이름을 사용하게 만든다고 명시되어 있다.
            // 따라서 service 속성에 내가 원하는 이름을 명시하여 event consumer 작업을 쉽게 모니터링할 수 있게 한다.
            .setAttribute("service", "spring-app")
            // setNoParent()를 호출해도 service override 를 회피할 수 있다.
            // 그러나 현재 span을 root span으로 만들어버리기 때문에 분산 추적이 어려워진다. 따라서 적절하지 않아보인다.
//            .setNoParent()
            .setAttribute("custom.test", "테스트 성공!")
            .startSpan()

        val scope = span.makeCurrent()
        try{
            return joinPoint.proceed()
        } catch (t: Throwable) {
            span.recordException(t)
            span.setStatus(StatusCode.ERROR)
            throw t
        } finally {
            scope.close()
            span.end()
        }
    }
}
