### 요구사항
#### 1. 블로그 검색

- 키워드를 통해 블로그를 검색할 수 있어야 합니다.
- 검색 결과에서 Sorting(정확도순, 최신순) 기능을 지원해야 합니다.
- 검색 결과는 Pagination 형태로 제공해야 합니다.
- 검색 소스는 카카오 API의 키워드로 블로그 검색(https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-blog)을 활용합니다.
- 추후 카카오 API 이외에 새로운 검색 소스가 추가될 수 있음을 고려해야 합니다.

#### 2. 인기 검색어 목록

- 사용자들이 많이 검색한 순서대로, 최대 10개의 검색 키워드를 제공합니다.
- 검색어 별로 검색된 횟수도 함께 표기해 주세요.


### 제약사항
1. api 호출은 ```FeignClinet```를 사용하여 진행하였며, 각 설정을 별도로 하지 않고, ```OpenFeignConfig```에서 처리함
2. open-api 호출시 발생하는 각각의 에러 사항은 특징별로 별도로 처리해야 하나, ```BlogSearchControllerAdvice```에서 공통으로 처리함