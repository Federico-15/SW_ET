<div align="center">
<a href="">
<img width="100%" src="발길닿는곳 이미지" alt="발길닿는곳 이동하기"/>
</a>

<!-- 이 부분을 나중에 이미지로 채울 수 있도록 빈칸으로 둡니다 -->


## 👻 Member

<table>
<tr>
<td align="center">FE & BE<strong></strong></td>
</tr>
  <tr>
    <td align="center" width="120px">
      <a href="">
        <img src="https://github.com/user-attachments/assets/0c559a2c-883b-4c5c-b07c-2578c26e2eaa" alt="류승환 프로필" />
      </a>
    </td>
  </tr>
  <tr>
     <td align="center">
      <a href="">
       류승환🦈
      </a>
    </td> 
  </tr>
</table>

# 이미지 
<!-- 이미지 넣기 -->
> 발길닿는곳은 서경대학교 SW-ET 팀이 개발한 여행 웹 추천 서비스 입니다.
>
> 개발 기간: 2024.04 - 2024.06

# 발길닿는곳

### ✨발길닿는곳? 유저를 위한 여행 추천 웹 사이트✨

</div>

## 🗨️ About 발길닿는곳

이 프로젝트는 공공데이터 포털과 한국관광공사가 운영하는 ‘대한민국 구석구석‘ API를 활용하여 사용자에게 지역별 여행지를 추천해주는 웹 서비스이다. Spring Boot로 개발된 이 프로젝트는 로그인된 사용자 권한, 게시판, 지역 카테고리화, 저장기능을 포함하고 있다.

사용자들은 카테고리 선택을 통해 맞춤형 여행지 정보를 제공받을 수 있다. 또한, 사용자들은 여행지에 대한 찜기능을 통해 인기 여행지를 확인하고, 리뷰 기능을 통해 개인적인 여행 정보를 공유할 수 있다. 이를 통해 사용자가 보다 효율적으로 휴식과 여가를 즐길 수 있도록 지원한다.



## 📦 주요 기능 


https://github.com/user-attachments/assets/2015ce78-4e29-4fc3-8379-f4be8d133ea0

https://github.com/user-attachments/assets/cc302473-747e-4ec7-9c54-523d86450c86



### USER SIDE
⚡️ 로그인/회원가입

⚡️ 여행지 목록(지역 및 상세지역별)

⚡️ 여행지 찜

⚡️ MyPage

⚡️ 여행지 리뷰 (CRUD, 좋아요-싫어요,댓글 기능)



## 💻 기술스택
<div align="center">
  <img src="https://github.com/user-attachments/assets/613d015c-1166-4e75-8b43-73aaae32beab" width="600px" alt="기술스택 아이콘">
</div>


## 🚀 시스템 구성도

<div align="center">
  <img src="https://github.com/user-attachments/assets/eb1a47ef-dbd8-4a22-9b08-a8fff3b55721" width="600px">
</div>



## 🛠️ 주요 의존성 패키지 버전

- **Spring Boot Starter Data JPA**: 3.0.3
- **Spring Boot Starter Security**: 3.0.3
- **Spring Boot Starter Web**: 3.0.3
- **Spring Boot Starter Thymeleaf**: 3.0.3
- **Spring Boot Starter Batch**: 3.0.3
- **Spring Boot Starter Mail**: 3.0.3
- **Spring Boot Starter Validation**: 3.0.3
- **Spring Boot Starter JDBC**: 3.0.3
- **MyBatis Spring Boot Starter**: 2.3.0
- **MariaDB Java Client**: 2.7.4
- **JWT (io.jsonwebtoken)**: 0.11.5
- **Thymeleaf Extras Spring Security6**: 3.1.1.RELEASE
- **Hibernate Validator**: 7.0.1.Final
- **Spring Security Crypto**: 6.0.1
- **SpringDoc OpenAPI Starter WebMvc UI**: 2.0.3
- **Lombok**: 1.18.26
- **Jakarta Servlet API**: 6.0.0
- **MySQL Connector Java**: 8.0.30
- **Swagger Annotations**: 1.6.2


## 📁 Project Structure
Model View Controller 컴포넌트 패턴을 사용했습니다.
```bash
├── 📦 build
├── 📦 gradle
├── 📦 out
├── 📦 src 
│   └── 📂 main
│       ├── 📂 java
│       │   └── 📂 SW_ET
│       │       ├── 📂 config
│       │       ├── 📂 controller
│       │       ├── 📂 DataSet
│       │       ├── 📂 dto
│       │       ├── 📂 entity
│       │       ├── 📂 exceptions
│       │       ├── 📂 repository
│       │       ├── 📂 service
│       │       └── 📄 Application.java
│       ├── 📂 resources
│       │   ├── 📂 static
│       │   ├── 📂 templates
│       │   └── 📄 application.yml
└── 
