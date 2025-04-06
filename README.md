## 목적

모니터링 도구를 조금 더 잘 활용하기 위해 학습하는 내용을 정리합니다. 학습에 참고한 자료는 다음과 같습니다.

- OpenTelemetry(https://opentelemetry.io/docs/what-is-opentelemetry/)

## 용어 정리

### Observability(관찰 가능성)

Observability는 출력을 검사하여 시스템의 내부 상태를 이해하는 ability입니다. 이는 trace, metric, log를 포함한 telemetry data(원격 측정 데이터)를 검사하여 시스템의 내부 상태를 이해할 수 있음을 의미합니다. 즉, 시스템의 내부 동작을 알지 못하여도 해당 시스템에 대한 질문을 함으로써 외부에서 시스템을 이해할 수 있도록 합니다.

시스템에 질문을 던지기 위해서는 애플리케이션이 instrument(계측)되어야 합니다. 즉, 애플리케이션 코드는 반드시 trace, metric, log과 같은 시그널을 내보내야 합니다. 그런 다음 instrumented data는 observability backend로 전송해야 합니다.

### Telemetry(원격 측정)

Telemetry는 시스템에서 내보내는 데이터와 동작을 말합니다. 데이터는 trace, metric, log의 형태로 제공될 수 있습니다.

#### Metric

Metric은 인프라 또는 애플리케이션에 대한 일정 기간 동안 **수치 데이터를 집계**한 것입니다. 예를 들면 시스템 에러 비율, CPU 사용률, 서비스에 대한 요청 비율 같은 것입니다. 

#### Span

Span은 **operation(작업) 단위**를 나타냅니다. span은 요청이 수행하는 특정한 특정 operation을 추적합니다. 해당 operation이 실행된 동안 어떤 일이 일어났는지 파악합니다.

Span에는 이름, 시간 관련 데이터, 구조화된 로그 메세지, 메타데이터가 포함되어 있어 추적하는 operation에 대한 정보를 제공합니다.

#### Trace(추적)

Trace는 마이크로 서비스나 서버리스 애플리케이션과 같은 multi-service architecture를 통해 전파되는 **요청(애플리케이션이나 사용자에 의해 만들어진)이 취한 경로를 기록**합니다.

trace는 **하나 이상의 span**으로 만들어집니다. 첫 번째 span은 root span을 나타냅니다. 각 root span은 요청의 시작부터 끝까지를 나타냅니다. 하위 span은 요청 중에 발생한 일에 대한 보다 심층적인 content를 제공합니다.

#### Log

Log는 서비스 또는 기타 component가 내보내는 타임 스탬프 메세지입니다. trace와는 달리 특정 사용자의 요청 또는 트랜잭션과 관련될 필요는 없습니다. 시스템의 모든 곳에서 찾을 수 있는 log를 말합니다.
<br/>
<br/>
