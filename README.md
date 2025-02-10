# WordWise : **영어 단어 학습 서비스**

## 📎 프로젝트팀: 돌아와조(1조)

## 👀 서비스 소개

Alan AI api를 활용한 영어 단어 학습 관련 다양한 서비스를 제공하는 웹 서비스

## 📅 프로젝트 기간

2025년 1월 9일 ~ 2025년 2월 10일 (4주)

## 개발 일정

- 2025.01.09 ~ 2025.01.13 : 기능 명세, ERD 작성, 협업 컨벤션 작성
- 2025.01.14 ~ 2024.02.06 : 기능 개발
- 2025.02.07 ~ 2025.01.10 : 최종 통합 및 디버깅

## ⭐ 주요 기능

## 단어 검색

- 단어의 뜻과 예문을 검색할 수 있는 서비스
- 사용자는 예문을 저장하거나, 마음에 안드는 예문을 재생성할 수 있음

## 단어장 서비스

- 사용자가 저장한 단어와 예문을 조회하는 서비스
- 등록한 사용자수 기준으로 단어 랭킹 조회 가능

## 단어 테스트 서비스

- 사용자가 저장한 단어 기반으로 단어 테스트를 제공하는 서비스
- 단어별 시도/틀린 횟수가 저장됨

## ⛏ 기술 스택

| 구분       | 내용                                                                                                                                                                                                                                    |
|----------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 기본 사용언어  | <img src="https://img.shields.io/badge/java-f89820?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=black"/>                    |
| Frontend | <img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=React&logoColor=black">                                                                                                                                  |
| Backend  | <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"> |
| 라이브러리    | <img src="https://img.shields.io/badge/daisyUI-1AD1A5?style=for-the-badge&logo=daisyui&logoColor=black">                                                                                                                              |
| 개발 도구    | <img src="https://img.shields.io/badge/intelliJ-000000?style=for-the-badge&logo=intellijidea&logoColor=white">                                                                                                                        |
| 서버 환경    |                                                                                                                                                                                                                                       |
| 데이터베이스   | <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">                                                                                                                                  |
| 협업 도구    | <img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white"/> <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white"/>                             |

## 초기 화면 설계

### 단어 검색 페이지

<img width="400" alt="word" src="https://github.com/user-attachments/assets/ff7b0020-4d33-4fa9-b16b-d34303c694dc" />

### 단어장 기능 페이지

<img width="400" alt="wordbook" src="https://github.com/user-attachments/assets/05b7cd03-453a-4c09-9855-a59e452c4791" />

### 단어 테스트 페이지

<img width="400" alt="wordtest" src="https://github.com/user-attachments/assets/3e5519a3-cc11-483f-8d49-2d5b45a093b0" />

### 회화 채팅

<img width="400" alt="chat" src="https://github.com/user-attachments/assets/fe52fcbc-7ddc-4b36-b724-5fb6ee2b9e0b" />

## API 명세서

### 로그인 및 회원 정보 기능

| url       | 기능   |
|-----------|------|
| `/`       | 홈    |
| `/login`  | 로그인  |
| `/signup` | 회원가입 |

### 단어 검색 기능

| 엔드포인트                | HTTP 메서드 | 기능         |
|----------------------|----------|------------|
| `/word`              | `GET`    | 단어/예문 검색 홈 |
| `/word/{word}`       | `GET`    | 단어/예문 검색   |
| `/word`              | `POST`   | 단어/예문 저장   |
| `word/{word}/reload` | `GET`    | 예문 새로고침    |

### 내 단어장 기능

| 엔드포인트               | HTTP 메서드 | 기능          |
|---------------------|----------|-------------|
| `/wordbook`         | `GET`    | 내 단어장 조회    |
| `/wordbook/{word}`  | `GET`    | 단어장 내 단어 검색 |
| `/wordbook/{id}`    | `GET`    | 단어장 상세 조회   |
| `/wordbook/{id}`    | `DELETE` | 단어장 삭제      |    
| `/wordbook/ranking` | `GET`    | 단어 랭킹 조회    |

### 단어 테스트 기능

| 엔드포인트                       | HTTP 메서드 | 기능        |
|-----------------------------|----------|-----------|
| `/wordtest`                 | `GET`    | 단어 테스트 생성 |
| `/wordtest/evaluate-answer` | `POST`   | 답안 체점     |

## 📌 ER 다이어그램

![FINAL)ERD](https://github.com/user-attachments/assets/3ba1e56a-8bb1-44c6-ab5e-a759eeadbe13)

## 🖥 화면 구성

### 메인 페이지

<img width="400" alt="word" src="https://github.com/user-attachments/assets/587e1fdc-a1d1-4529-a63a-25b8c04750b0" />

### 단어 검색 페이지

<img width="400" alt="wordbook" src="https://github.com/user-attachments/assets/c8126586-c9e0-4156-810d-11ea4a60df30" />

### 단어장 페이지

<img width="400" alt="wordtest" src="https://github.com/user-attachments/assets/ca56962d-bb52-4a79-95fe-a8908fac9ee3" />

### 단어 랭킹 페이지

<img width="400" alt="chat" src="https://github.com/user-attachments/assets/262df6b1-33f8-44e3-83c9-e7328867953c" />

### 단어 테스트 페이지

<img width="400" alt="chat" src="https://github.com/user-attachments/assets/378a3e5f-1deb-4705-9e2a-0079e65596be" />

### 회화 채팅

<img width="400" alt="chat" src="https://github.com/user-attachments/assets/9fd92ef6-4718-4335-8a27-3e16a185c33d" />

## 👨‍👩‍👦‍👦 팀원 역할

<table>
  <tr>
    <td align="center"><img src="https://item.kakaocdn.net/do/fd49574de6581aa2a91d82ff6adb6c0115b3f4e3c2033bfd702a321ec6eda72c" width="100" height="100"/></td>
    <td align="center"><img src="https://mb.ntdtv.kr/assets/uploads/2019/01/Screen-Shot-2019-01-08-at-4.31.55-PM-e1546932545978.png" width="100" height="100"/></td>
    <td align="center"><img src="https://mblogthumb-phinf.pstatic.net/20160127_177/krazymouse_1453865104404DjQIi_PNG/%C4%AB%C4%AB%BF%C0%C7%C1%B7%BB%C1%EE_%B6%F3%C0%CC%BE%F0.png?type=w2" width="100" height="100"/></td>
  </tr>
  <tr>
    <td align="center"><strong>문영훈</strong></td>
    <td align="center"><strong>이경돈</strong></td>
    <td align="center"><strong>오상훈</strong></td>
  </tr>

  <tr>
   <td align="center"><b>PM/Front/Back/DB</b>
    <br>로그인, 단어 테스트, 통계 기능, 프론트 개발
  </td>

   <td align="center"><b>Back/Front/DB</b>
    <br>데이터 모델링, 단어 검색, 단어장 기능, 프론트 개발
  </td>

  <td align="center"><b>Front/Docs</b>
    <br>프론트 개발, 문서화 작업
  </td>
 </tr>
</table>

