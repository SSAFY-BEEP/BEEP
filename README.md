# BEEP
<img src="./exec/images/beepalerticon.png" width="300" />
</br>

## 프로젝트 개요

- 명칭 : BEEP
- 소개 : 프로젝트는 기존의 메신저 앱들과 차별된 새로운 소통의 재미를 주기 위해 80~90년대 유행한 무선 호출기인 삐삐를 재해석해서 만든 애플리케이션입니다. 
- 진행 기간: 2022.10.10(월) ~ 2022.11.21(월)
- UCC: [Youtube 링크](https://www.youtube.com/watch?v=Zjsa6lmApLA)

</br>

----

</br>

## 팀원 소개

|                      팀장                       |                       팀원                       |                      팀원                      |                      팀원                      |                 팀원                 |
| :---------------------------------------------: | :----------------------------------------------: | :--------------------------------------------: | :--------------------------------------------: | :----------------------------------: |
|                     김현열                      |                      김태하                      |                     호인영                     |                     박예진                     |                황준원                |
|                     Android                     |                     Android                      |                    Android                     |                    Back-end                    |               Back-end               |
| 회원관리<br /> 설정 관리<br /> 디버깅<br /> UCC | 메세지 관리<br /> 음성 녹음/재생 관리<br /> 발표 | JIRA<br /> 삐삐페이지 키보드<br /> 연락처 관리 | Redis, DB<br /> 메세지, 프리셋<br /> 차단, PPT | Security, FCM<br /> DB, 회원, 연락처 |

</br>

----

</br>

## 기술 스택

<h3 align="center">Back-end</h3>



<p align="center">
	<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
    <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
    <img src="https://img.shields.io/badge/spring security-6DB33F?style=for-the-badge&logo=spring security&logoColor=white">
    <img src="https://img.shields.io/badge/hibernate-59666C?style=for-the-badge&logo=hibernate security&logoColor=white">
</p>



<h3 align="center">Android</h3>
<p align="center">
	<img src="https://img.shields.io/badge/kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white">
    <img src="https://img.shields.io/badge/android-6DB33F?style=for-the-badge&logo=android&logoColor=white">
    <img src="https://img.shields.io/badge/jetpack compose-4285F4?style=for-the-badge&logo=jetpack compose&logoColor=white">
    <img src="https://img.shields.io/badge/firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=white">
</p>


<h3 align="center">DB</h3>
<p align="center">
	<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
	<img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white">
	<img src="https://img.shields.io/badge/amazon s3-569A31?style=for-the-badge&logo=amazon s3&logoColor=white">
</p>



<h3 align="center">CI/CD</h3>
<p align="center">
	<img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
	<img src="https://img.shields.io/badge/ubuntu-E95420?style=for-the-badge&logo=ubuntu&logoColor=white">
	<img src="https://img.shields.io/badge/jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white">
    <img src="https://img.shields.io/badge/amazon ec2-FF9900?style=for-the-badge&logo=amazon ec2&logoColor=white">
</p>


## 주요 기능

### 메시지 송/수신
<table>
    <tr>
        <td align="center">
            <img src="./exec/images/번호송신.gif" alt="번호로 송신" width="200" />  
            <h5>번호로 송신</h5>
        </td>
        <td align="center">
            <img src="./exec/images/인사말듣기.gif" alt="인사말듣기" width="200"/> 
            <h5>인사말 듣기</h5>
        </td> 
        <td align="center">
            <img src="./exec/images/녹음및전송.gif" alt="녹음및전송" width="200"/> 
            <h5>녹음 및 송신</h5>
        </td>
    </tr>
</table>

- 초성과 숫자만 사용
- 연락처 목록에서 바로 송신 가능
- 최근에 받은 메시지에서 상대에게 답장 가능
- 익명으로 송/수신 됨
- 음성을 녹음해서 메시지와 함께 송신 가능
- 음성을 녹음하기 전에 상대방의 인사말을 들을 수 있음

</br>

### 메시지 목록

<table>
    <tr>
        <td align="center">
            <img src="./exec/images/보관.gif" alt="보관" width="200" />  
            <h5>메시지 보관</h5>
        </td>
        <td align="center">
            <img src="./exec/images/태그.gif" alt="태그" width="200" />  
            <h5>메시지 태깅</h5>
        </td> 
        <td align="center">
            <img src="./exec/images/차단.gif" alt="차단" width="200" />  
            <h5>메시지 차단</h5>
        </td>
    </tr>
</table>

- 24시간 후에 사라짐
- 메시지 보관으로 영구 보관 가능
- 메시지에서 상대를 차단
- 음성 메시지를 재생 가능

</br>

### 삐삐 사전
<img src="./exec/images/사전.gif" alt="사전" width="200" />

- 예전에 삐삐에서 사용한 숫자 암호를 사전으로 구성
- 숫자 단어를 통해서 검색 가능

</br>

### 메시지 및 연락처 단축키
<img src="./exec/images/단축키.gif" alt="단축키" width="200" />

- 메시지 송신에서 번호를 길게 눌러서 사용
- 설정 화면에서 메시지, 연락처 별로 설정 가능

</br>

### 삐삐 꾸미기

<table>
    <tr>
        <td align="center">
            <img src="./exec/images/테마.gif" alt="테마" width="200" />
            <h5>테마 설정</h5>
        </td>
        <td align="center">
            <img src="./exec/images/각인.gif" alt="각인" width="200" />
            <h5>각인 설정</h5>
        </td> 
        <td align="center">
            <img src="./exec/images/폰트.gif" alt="폰트" width="200" />
            <h5>폰트 설정</h5>
        </td>
    </tr>
</table>

- 메인 화면의 삐삐 색 변경 가능 (테마)
- 앱의 전체적인 폰트 변경 가능
- 삐삐에 자신만의 각인 설정 가능



## [포팅매뉴얼](exec/Readme.md)
