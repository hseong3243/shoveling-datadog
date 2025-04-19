## Signals

Signal은 운영체제와 애플리케이션의 활동을 설명하는 시스템 출력입니다. signal은 다음과 같이 설명할 수 있습니다.
- 온도나 메모리 사용률처럼 특정 시점에 측정하고자하는 것
- 추적하길 원하는 분산 시스템에서 구성 요소간에 벌어지는 이벤트

서로 다른 signal을 그룹화하여 내부 작업을 관찰 가능합니다.

signal에는 다음과 같은 종류가 있습니다.

- Traces
- Metrics
- Logs
- Baggage

## Traces

tarce는 애플리케이션에 요청이 이루어질 때 발생하는 큰 그림을 제공합니다. tarce는 애플리케이션의 요청이 이루어지는 전체 경로를 이해하는 데 필수적입니다.

trace는 하나 이상의 span으로 구성됩니다. opentelemetry에서 보여주는 예제를 조금 더 간략화하여 하나 이상의 span이 어떻게 trace를 구성하는지 알아보겠습니다.

1. `hello` span
```json
{
  "name": "hello",
  "context": {
    "trace_id": "5b8aa5a2d2c872e8321cf37308d69df2",
    "span_id": "051581bf3cb55c13"
  },
  "parent_id": null,
  ...
}
```
- `hello` span은 parent_id가 없습니다. 즉, 이 span이 trace의 root span임을 의미합니다.


2. `hello-greetings` span
```json
{
  "name": "hello-greetings",
  "context": {
    "trace_id": "5b8aa5a2d2c872e8321cf37308d69df2",
    "span_id": "5fb397be34d26b51"
  },
  "parent_id": "051581bf3cb55c13",
  ...
}
```
- `hello-greetings` span은 parent_id로 `hello` span의 span_id를 가집니다.
- trace_id 역시 `hello` span과 동일합니다. 즉, `hello`와 `hello-greetings`는 동일한 trace의 일부임을 의미합니다.

3. `hello-salutations` span
```json
{
  "name": "hello-salutations",
  "context": {
    "trace_id": "5b8aa5a2d2c872e8321cf37308d69df2",
    "span_id": "93564f51e1abe1c2"
  },
  "parent_id": "051581bf3cb55c13",
  ...
}
```
- `hello-salutations`도 trace_id와 parent_id를 통해 `hello`와 동일한 trace의 일부임을 알 수 있습니다.

세 span이 동일한 trace_id, parent_id를 가지며 계층 구조를 이루며 하나의 trace를 구성하는 것을 알 수 있습니다.

## Tracer Provider

Trace provider는 tracer를 위한 factory입니다. 애플리케이션의 수명주기와 일치하며 한 번만 초기화됩니다. 또한, tracer provider의 초기화에는 resource와 exporter의 초기화가 포함됩니다.

## Tracer

Tracer는 서비스의 request와 같이 주어진 operation에 대한 자세한 정보가 포함된 span을 만듭니다.

## Trace Exporters

Trace Exporter는 consumer에게 trace를 보냅니다. consumer는 opentelemetry collector나 오픈 소스 또는 벤더사의 백엔드가 될 수 있습니다.

## Context Propagation

Context Propagation은 분산 추적을 가능하게 하는 핵심 개념입니다. span이 어디서 생성되든지 관계없이 하나의 trace로 합쳐질 수 있습니다.

## Spans

span은 operation 또는 작업의 단위를 나타냅니다. span은 trace의 building block입니다. opentelemetry에서는 다음과 같은 정보가 포함되어 있습니다.

- 이름
- 부모 span Id
- 시작/종료 타임 스탬프
- span context
- attributes(span의 메타데이터)
- span events
- span links
- span status

다음은 opentelemetry 문서에서 예제로 제공하는 span입니다.
```json
{
  "name": "/v1/sys/health",
  "context": {
    "trace_id": "7bba9f33312b3dbb8b2c2c62bb7abe2d",
    "span_id": "086e83747d0e381e"
  },
  "parent_id": "",
  "start_time": "2021-10-22 16:04:01.209458162 +0000 UTC",
  "end_time": "2021-10-22 16:04:01.209514132 +0000 UTC",
  "status_code": "STATUS_CODE_OK",
  "status_message": "",
  "attributes": {
    "net.transport": "IP.TCP",
    "net.peer.ip": "172.17.0.1",
    "net.peer.port": "51820",
    "net.host.ip": "10.177.2.152",
    "net.host.port": "26040",
    "http.method": "GET",
    "http.target": "/v1/sys/health",
    "http.server_name": "mortar-gateway",
    "http.route": "/v1/sys/health",
    "http.user_agent": "Consul Health Check",
    "http.scheme": "http",
    "http.host": "10.177.2.152:26040",
    "http.flavor": "1.1"
  },
  "events": [
    {
      "name": "",
      "message": "OK",
      "timestamp": "2021-10-22 16:04:01.209512872 +0000 UTC"
    }
  ]
}
```

## Span Context

span context는 다음을 포함하는 모든 span의 불변 객체입니다.

- traceId
- spanId
- trace flag: trace에 대한 정보가 포함된 이진 인코딩
- trace state: 벤더사 별 trace 정보를 전달할 수 있는 key-vale 쌍

## Attributes

attribute는 span에 주석을 달아서 추적중 인 operation에 대한 정보를 전달하기 위해 사용할 수 있는 메타데이터가 포함된 key-value 쌍입니다. 예를 들면 e-commerce 서비스에서 사용자의 id, 카트 id, 상품 id와 같은 정보를 attribute에 추가할 수 있습니다.

이는 span이 생성중이거나 생성 후에 추가할 수 있습니다. 

## Span Status

각 span은 다음과 같은 상태를 가집니다.

- Unset
- Error
- Ok

기본 값은 `Unset`입니다. `Unset`는 에러 없이 추적한 operation이 성공적으로 완료되었음을 의미합니다.

`Error`는 추적중인 operation에서 오류가 발생했다는 것을 의미합니다. 가령 http 500 에러 같은 것입니다.

`Ok`는 개발자가 에러가 없다는 것을 명시적으로 표시했음을 의미합니다. 개발자가 성공 외에 다른 해석이 없기를 바라는 상황에서 도움이 됩니다. 대부분의 경우 개발자가 명시저그올 `Ok`를 표시할 필요는 없습니다.

## Span Kind

Span은 `client`, `server`, `internal`, `producer`, `consumer` 중 하나로 생성됩니다. 이러한 span kind는 trace를 어떻게 조립해야하는지 tracing backend에게 힌트를 제공합니다.

#### Client

client span은 http 요청 또는 데이터베이스 호출과 같은 밖으로 나가는 동기식 호출을 나타냅니다.

#### Server

server는 HTTP 요청과 같이 안으로 들어오는 동기식 호출을 나타냅니다.

#### Internal

internal은 프로세스 경계를 넘지 않는 operation을 나타냅니다. 함수 호출같은 것에 사용할 수 있습니다.

#### Producer

producer는 비동기로 처리될 수 있는 작업의 생성을 나타냅니다.

#### Consumer

consumer는 producer가 만든 작업을 처리하는 것을 나타냅니다.

## Span Events

span event는 span의 구조화된 로그 메세지로 생각할 수 있습니다. 일반적으로 span duration 동안 의미 있는 단일 시점을 나타내는 데 사용됩니다. 작업이 완료된 타임스탬프가 의미 있거나 관련이 있는 경우 span event를 사용합니다. 만일 이러한 시점이 의미 없다면 attribute를 사용할 수 있습니다.

## Span Links

link는 하나의 span을 하나 이상의 다른 span과 연결하여 인과관계를 알리기 위해 존재합니다. 

예를 들면, 비동기적으로 실행되는 작업이 하나 있다고 해봅시다. 이 작업이 언제 실행될지는 알 수 없지만 이 작업을 요청한(아마도 큐에 삽입한) 작업이 포함된 trace와 연관시키고 싶을 수 있습니다. 여기서 사용할 수 있는 것이 span link입니다.
