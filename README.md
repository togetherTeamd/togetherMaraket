# TogetherMaraket
**투게더 마켓**은 판매자와 사용자 간에 거래 장소까지의 간편하고 직관적인 **길찾기 기능** UI를 제공한다. 
또한 직거래 시, 편리하고 안전한 중고거래 플랫폼 사용을 위해 **QR 송금** 시스템이 설계되었다. 
중고 물품을 검색할 때 필요한 **필터링 기능**을 세분화하여 항목을 더 구체화하고 물품의 상태, 거래 형태를 설정하여 원하는 항목의 물품을
정확하게 확인할 수 있게 하며 직거래 시 판매자와 구매자 간 편리하게 거래 약속과 길찾기 소통을 하도록 **채팅 시스템**도 제공한다.



## 개요
펜데믹으로 인한 경기침체로 소비심리가 위축되어 한국의 소비 트렌드가 변화되고 있다. 이에 따라 언택트 시대에서 필요한 물건을 저렴하게 
구매할 수 있는 중고 플랫폼의 수요와 관심이 높아지고 그만큼 중고거래 피해 발생 건수도 늘어나고 있는 추세로 이에 판매자와 구매자 간에 
신뢰 있는 안전한 거래와 거래의 편리성이 점점 중요해지고 있다. 본 프로젝트는 중고거래 사용자 간의 신뢰도를 높이고 중고 물품의 거래를 분석하여 
온라인에서 사용자의 지역 기반의 거래 환경을 안전하고 편리하게 제공하는 새로운 방식의 플랫폼을 제시한다.

## 개발환경
```
* IDE : Intellij 2021.3.2 Ultimate Edition
* RDBMS : MySQL 8.0
* Back-End : Spring Boot 2.6.4
* Front-End : Thymeleaf 3.0
* Library : Lombok, json, Gradle, spring-security, stomp-websocket ... etc
* Api : KAKAO API, TMAP API, CoolSMS API
```

## 시스템 설계
![시스템 구성도](https://user-images.githubusercontent.com/79970349/168969800-a4a1c2a9-5662-4811-ba38-dde242f5c6ba.png)

본 프로젝트의 **Back-End** 개발 프레임 워크인 **Spring Framework**를 사용하여 클라이언트의 요청을 받은 컨트롤러가 결과값을 뷰에 전달하여 알맞은 응답을 생성하도록 한다.
**Front-End** 개발 프레임워크는 Spring 프레임워크와 호환이 잘 되는 **Thymeleaf**를 사용했다.
**스프링 MVC 패턴**의 흐름은 웹 브라우저에서 실행을 요청하면, 요청을 받은 웹 서버가 서블릿 컨테이너로 넘겨준다. 이 후 **URL**을 확인하여 적합한 **Method**를 찾는다. 요청을 
처리할 서블릿을 탐색하고 실행한 결과는 **Model**에 저장한다. **Model** 객체에서 요청받은 화면을 표시하기 위해 필요한 결과 창을 **Client**에게 넘겨주어 웹브라우저는 서버로부터 
요청받은 내용이 화면에 표시한다. 또한, **SQL 쿼리**를 통한 데이터 질의문을 전달하여 데이터베이스가 반환한 데이터를 획득하게 된다. 뿐만 아니라 빠른 데이터의 접근을 위해 
SPRING에서 지원하고 있는 ORM 표준 기술인 **Spring JPA**를 사용했다.

## 기능

* 비회원
  * 상품 조회
  * 상품 검색
    <br>**로그인하지 않은 비회원 유저는 상품의 조회, 검색 기능만 이용할 수 있도록 설정해 두었다.**
    
* 회원
  * 상품 조회
  * 상품 검색
  * 상품 등록
  * 거래장소 길찾기
  * 관심 상품 찜
  * 거래
  * 신고
  * 문의
  * 채팅
  * QR거래
  * 내 위치 등록
  <br>**상품 상세보기 페이지에서 길찾기, 채팅기능을 이용할 수 있으며, QR기능은 메인 페이지에서 자신의 계좌 정보를 연동한 뒤에 이용할 수 있다. 나머지 기능은
  UI에서 지관적으로 쉽게 이용할 수 있을것이다.**
  
* 관리자
  * 공지사항 등록
  * 회원 관리
  * 신고/문의 내역 확인
  <br>**아이디를 만들때 아이디를 'admin'으로 생성하면 관리자로권한을 얻은 회원으로 생성이 되며 로그인시 관리자페이지로 매핑이 되며 위의 기능을 이용할 수 있다.**
## Contributors
This project exists thanks to all the people who [contribute](https://github.com/togetherTeamd/togetherMaraket/graphs/contributors).


<a href="https://github.com/joyfulviper">
<img src="https://avatars.githubusercontent.com/u/79970349?s=400&v=4" height="50" alt="joyfulviper"/></a>
<a href="https://github.com/Leehyoju97">
<img src="https://avatars.githubusercontent.com/u/83864280?s=32&v=4" height="50" alt="Leehyoju97"/></a>
<a href="https://github.com/yeseul9231">
<img src="https://avatars.githubusercontent.com/u/65112086?s=32&v=4" height="50" alt="yeseul9231"/></a>
<a href="https://github.com/dldpdnjs21">
<img src="https://avatars.githubusercontent.com/u/65122491?s=32&v=4" height="50" alt="dldpdnjs21"/></a>
<a href="https://github.com/qweqwerq">
<img src="https://avatars.githubusercontent.com/u/88891704?s=32&v=4" height="50" alt="qweqwerq"/></a>
<a href="https://github.com/minroco">
<img src="https://avatars.githubusercontent.com/u/35948766?v=4" height="50" alt="minroco"/></a>


  
  
  
  
  
  
  
  
  
  
  
  
