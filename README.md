### 서비스 이용 방법

```bash

java -jar kakaobank.jar

```
#### [kakaobank.jar 다운로드 링크](https://drive.google.com/file/d/1bzR-ULHVhSZcnbCjYg41hqKwUMy2vyAj/view?usp=share_link)
#### [kakaobank.jar.zip 다운로드 링크](https://drive.google.com/file/d/1mQEo0W5zqQszHR8CPVNmLySjZF9Dap5s/view?usp=sharing)

아래의 링크를 사용하여 서비스 접속 가능
- [rest-docs 화면](http://localhost:8080/static/docs/index.html)
- [kakao-blog-search 화면](http://localhost:8080/blog/search?query=test&page=1&size=2&source=kakao)
- [naver-blog-search 화면](http://localhost:8080/blog/search?query=test&page=1&size=2&source=naver)
- [인기 검색어 결과 화면](http://localhost:8080/blog/search/keyword/ranking)
--- 

### 제약사항
1. api 호출은 ```FeignClinet```를 사용하여 진행하였며, 각 설정을 별도로 하지 않고, ```OpenFeignConfig```에서 처리
2. open-api 호출시 발생하는 다양한 에러 사항은 특징 별로 별도로 처리해야 하나, 제한된 시간에 예외처리가 필요하여 ```BlogSearchControllerAdvice```에서 공통으로 처리

### 요구사항 대응 내용
#### [블로그 검색 기능]
블로그 검색에 필요한 정보는 아래와 같이 reqeust parameters정보를 통해서 대응 하였습니다.
<img width="989" alt="image" src="https://user-images.githubusercontent.com/1481137/226822912-a22fb427-8d76-4080-a3c3-1b6d90f8c70c.png">
#### [검색 결과 Pagination]
Spring에서 제공하는 Page 클래스를 통해서 대응하였습니다.
<img width="1410" alt="image" src="https://user-images.githubusercontent.com/1481137/226823603-336801f2-8068-4a21-8432-43e8fc6ce237.png">

#### [확정성 고려 사항]
- 외부 open-api가 추가될 것으로 예상되어 아래와 같이 ```BlogSearchApiClient```, ```BlogSearchResults```, ```BlogSearchResult``` 인터페이스로 대응하였습니다.
<img width="1771" alt="image" src="https://user-images.githubusercontent.com/1481137/226823981-b3410c24-67e1-41ec-a397-8d721629c89e.png">

##### [인기검 색어 기능]
- 구현 제약사항으로 H2 DB를 사용해야 하기 때문에 인기검색어 count 수를 계산할 때 insert, update가 빈번하게 발생할 것으로 예상되어, [caffeine-cache](https://github.com/ben-manes/caffeine) 를 사용하여, count수를 계산한하고 적절한 시기에 DB에 update될수 있도록 구성하였습니다. 
- 메인 서비스는 블로그 검색이기 때문에 메인 thread들은 open-api의 결과를 취합하여 response하는데 사용하고, Spring Event, Cache 를 활용하여 인기 검색어 count 취합 비동기로 h2디비에 저장하는 방식을 채택하였습니다. (이 방식으로 처리하게 되면 인기 검색어를 취합하는데 실패 하더라도 메인 서비스에는 영향을 받지 않을 것으로 예상됩니다.)
- caffeine cache는 글로벌 캐시가 아니기 때문에 동시성 이슈가 발생할수 있습니다. 그래서, db에 저장 할때, ```Transactional``` 설정과 ```spring.jpa.open-in-view: false```으로 영속성 영역을 제한으로 DB에서 동기화 될수 있도록 고민하였습니다. 이외에도 다양한 이슈로 동시성 문제가 발샐할 수 있어 NoSQL 서버를를 활용한 방법으로 개선의 여지가 남아 있다고 생각합니다. 이를 위해서, 코드 레벨에서 ```BlogSearchEventHandler```를 통해서 이벤트 방식으로 분리해 놓았습니다.
<img width="2436" alt="image" src="https://user-images.githubusercontent.com/1481137/226825309-179f2274-2d89-4fa0-bdb6-3e1dbcb35c15.png">
