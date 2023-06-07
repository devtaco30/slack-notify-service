# SLACK NOTIFY SERVICE 
- Using Kafka, Each MSA send Message to Destination

## Project Description
- SNT : SLACK NOFTIFY SERVICE
- Kafka 를 활용한 Message Broker 서비스로, MSA 들의 인포,에러 로그 아이템을 가져와 Destination 으로 전달합니다.

## Package Structure
- Description
  - config : Spring, Kafka, Slack Config 설정이 있는 패키지.
  - entity: table 혹은 document 와 1:1 대응되는 entity 클래스들이 들어가는 패키지.
  - repository : repository 클래스들이 들어가는 패키지 (Persistence 역할 수행).
  - service : 비즈니스 로직을 처리하는 클래스들이 존재하는 패키지.
  - utils : Util 클래스가 존재하는 패키지.
- 해당 구조에 없는 패키지가 추가해야될 경우 개발자 재량에 의해 추가 할 수 있습니다. 

## Application Profile Strategy
- test
  - 테스트 코드가 실행되는 profile입니다.
  - 테스트 코드에서 의존 구성요소를(e.g. 외부 통신, 다른 서비스 등) 사용할 수 없을 때는 **테스트 더블**을 사용하여 테스트합니다.
- local
  - 로컬 환경에서 실행되는 profile입니다.
  - 의존 구성요소는 /resource/snts-docker-db/docker-compose를 통해 실행합니다.
  - docker-compose로 해결 할 수 없는 경우 Profile별로 별도 로직을 수행할 수 있도록 합니다.

## Installation and Run (for local)
- local 환경에서의 경우 third party(e.g. Database, cache 등)를 docker-compose를 통해서 구성합니다.
- docker-compose를 통해 local 환경을 구성하는 이유?
  1. 각 개발자가 서로 다른 환경(e.g. OS 등)이더라도 최대한 비슷한 구조 또는 환경으로 개발하기 위해 사용합니다.
  2. 각 개발자가 Docker를 제외한 추가 설치가 없이 실행 할 수 있느 편의성을 제공합니다. (단, 별도 Alien 통신을 경우 예외 일 수 있습니다.)
- 실행 순서
  1. Project Root에서 아래와 같이 명령어를 입력합니다.
  ```
  /src/main/resources/snts-docker-db/docker-compose up -d 
  ```
  2. 로컬환경에서 --spring.profiles.active=local를 추가 후 Application을 실행합니다.

## Test Case
- 테스트 케이스는 개발자가 개발한 로직을 <span style="color:red">**유의미**</span>히게 검증 하기 위해 사용합니다.
- 테스트 케이스는 Class 혹은 Method 별로 @DisplayName 이용하여 설명합니다.
- 기본적으로 BDD(Given-When-Then) 패턴을 통해 작성하지만 필요한 경우 DCI(Describe-Context-It) Pattern를 통해 작성해도 무방합니다. (패턴의 통일보다는 로직을 검증하는데 중점을 두는 방향)
- testContainer 를 사용하여, 단위 및 통합 테스트를 진행합니다.

## CI/CD Principle
- TBD
