## 버전 정보

- 호스트 머신(EC2) 설정
    - Ubuntu : `20.04.05`
    - Docker : `20.10.18`
    - Docker-compose : `1.26.0`
    - Nginx : `1.18.0`
    - Redis : `7.0.5`
- Front-end 설정
    - Android : `7.3.1`
        - Compose UI : `1.2.0`
        - hilt : `2.44`
        - accompanist : `0.25.1`
        - Retrofit : `2.9.0`
        - minsdk : `21`
        - targetsdk : `32`
        - viewmodel-compose :  `2.5.1`
        - activity-compose : `1.3.1`
        - navigation-compose : `2.5.1`
    - Kotlin : `1.7.2`
- Back-end 설정
    - openjdk version `"1.8.0_192”`
    - OpenJDK Runtime Environment (Zulu 8.33.0.1-win64) (build 1.8.0_192-b01)
    - OpenJDK 64-Bit Server VM (Zulu 8.33.0.1-win64) (build 25.192-b01, mixed mode)
    - Spring Framework : `2.7.4`
    - Mysql : `5.7`
- IDE 설정
    - IntelliJ 2022.1.3
    - AndroidStudio 2021.3.1 Dolphin
    - Emulator : Pixel 5 API 33
    

## EC2 원격 접속

### MobaXTerm 설치

다운로드 링크 : 

[MobaXterm free Xserver and tabbed SSH client for Windows](https://mobaxterm.mobatek.net/download.html)

### 설치 후 연결

1. 실행 후에 왼쪽 상단의 Session 클릭
2. SSH 연결

![원격접속.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/33ac2d99-7442-4b6a-8392-5b40b99b320a/%EC%9B%90%EA%B2%A9%EC%A0%91%EC%86%8D.png)

- Remote host에 Public IP 입력 (k7a406.p.ssafy.io)
- Specify username에 사용할 유저 이름 입력 (현 프로젝트는 ubuntu 사용)
- Use private key에 받은 pem키 입력
- OK 누르면 연결 완료

## Docker와 Docker-compose 설치

### Docker

1. 유틸 설치
    
    ```bash
    sudo apt update
    sudo apt install apt-transport-https ca-certificates curl software-properties-common
    ```
    
2. 키 생성
    
    ```bash
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
    ```
    
3. Repository 추가 후 Update
    
    ```bash
    sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
    sudo apt update
    ```
    
4. Docker 설치
    
    ```bash
    sudo apt install docker-ce
    ```
    
5. Docker 설치 확인
    
    ```bash
    sudo systemctl status docker
    ```
    
6. Docker 권한 설정 (설정한 유저 이름)
    
    ```bash
    # USER = ubuntu
    sudo usermod -aG docker ${USER}
    ```
    
7. Permission denied 가 뜰 때
    
    ```bash
    sudo chmod 666 /var/run/docker.sock
    ```
    

### Docker-compose

1. Docker-compose 설치
    
    ```bash
    sudo curl -L "https://github.com/docker/compose/releases/download/1.26.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
    ```
    
2. 심볼릭 링크 생성, 실행 권한 부여
    
    ```bash
    sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
    sudo chmod +x /usr/bin/docker-compose
    ```
    
3. 설치 확인
    
    ```bash
    docker-compose -v
    ```
    

## DB Init

1. 서비스 배포를 위한 디렉토리 생성
    
    ```bash
    cd /home/ubuntu
    mkdir beep
    cd beep
    ```
    
2. db 설정을 위한 디렉토리 생성
    
    ```bash
    mkdir db
    ```
    
    - `db` : 스키마와 유저를 생성하기 위한 sql이 들어가는 디렉토리
3. `init.sql` 작성
    
    ```bash
    vim ./db/init.sql
    ```
    
    - 만약 vim이 설치되어 있지 않을 경우 vim 설치 후 진행
        
        ```bash
        sudo apt-get update
        sudo apt-get install vim
        ```
        
    - `init.sql` 내용
        - DB와 User를 생성. (beep 과 a406)
        - User에 모든 권한을 주는 명령어
        - DB UserId : a406 / Password : ssafyA406
        
        ```bash
        create database IF NOT EXISTS `beep` collate utf8mb4_general_ci;
        create user 'a406'@'%' identified by 'ssafyA406';
        grant all privileges on *.* to a406@'%';
        flush privileges;
        ```
        
4. MySQL과 기본 실행 쿼리를 위한 `docker-compose.yml` 내용 (프로젝트의 최상위 폴더에 작성) 
    
    ```json
    docker-compose version
    version: "3.8"
    
    #container list
    services:
      db:
        image: mysql:5.7
        container_name: mysql
        restart: always
        volumes:
          # Mount cotainer drive to real drive
          - ./db:/docker-entrypoint-initdb.d
        networks:
          # network inside of a container
          - app-network
        ports:
          - "3306:3306"
        environment:
          MYSQL_ROOT_PASSWORD: ssafyA406
          TZ: "Asia/Seoul"
        privileged: true
    ```
    
    - 해당 내용은 추가적인 내용들을 모두 작성 후 아래에서 `docker-compose up` 할 예정입니다.
    - 현재는 DB의 데이터를 volume 설정 해놓지 않아서 컨테이너가 종료되면 데이터가 사라짐
        - 설정을 위해서는 `-/db/<임의의 폴더명>:/var/lib/mysql` 를 추가

## Redis 설정

1. `beep` 디렉토리에서 `conf` 디렉토리 생성
2. conf 디렉토리로 이동 후 redis.conf 파일 생성 (파일 내용 참조)
    
    [redis.conf](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/4cfc8bf6-6c55-42bc-a789-99ec98a2b07f/redis.conf)
    
3. `docker-compose.yml` 파일에 redis 컨테이너 설정 추가
    
    ```json
    redis:
        image: redis
        command: redis-server --port 6379
        container_name: redis
        hostname: redis
        labels:
          - "name=redis"
          - "mode=standalone"
        ports:
          - "6379:6379"
    ```
    

## BackEnd 배포

### Local에서 빌드하여 jar 파일 실행하기

1. Local 워크스페이스 `/beep` 경로에서 터미널로 빌드 
    
    ```bash
    ./gradlew clean build
    #또는
    ./gradlew bootJar
    ```
    
2. 서버 접속 후 Build한 파일을 넣을 폴더 생성
    
    ```bash
    cd /home/ubuntu/beep
    mkdir build
    ```
    
3. 로컬에서 /backend/build/libs/<파일명>.jar 경로에 생성된 jar 파일을 서버에 업로드
    - 경로 : `/home/ubuntu/beep/build/libs`
4. Dockerfile을 `beep` 폴더에 생성
    
    ```java
    FROM openjdk:8-jdk-alpine
    ARG JAR_FILE=build/libs/*.jar
    COPY ${JAR_FILE} app.jar
    ENTRYPOINT ["java", "-jar", "/app.jar"]
    ```
    
    - 추후에 젠킨스를 사용할 때 같은 Dockerfile을 사용하기 위해서 JAR_FILE 경로를 build/libs로 설정했습니다.
    
5. Build한 jar를 실행하기 위한 `docker-compose.yml`
    
    ```bash
    vim docker-compose.yml
    ```
    
    - 위치 `ubuntu/beep`
    - 내용
        
        ```bash
        # docker-compose version
        version: "3.8"
        
        #container list
        services:
          dnti:
            build:
              context: ./
            container_name: beep
            restart: always
            ports:
              - "8081:8081"
            environment:
              TZ: "Asia/Seoul"
              # Spring application.properties DB
              SPRING_DATASOURCE_URL: "jdbc:mysql://db:3306/beep?userUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Seoul&zeroDateTimeBehavior=convertToNull&rewriteBatchedStatements=true"
              server.address: 0.0.0.0
        			JWT_SECRET: ${JWT_SECRET}
        	    ACCESS_KEY: ${ACCESS_KEY}
        	    SECRET_KEY: ${SECRET_KEY}
        	    SMS_KEY: ${SMS_KEY}
        	    SMS_SECRET: ${SMS_SECRET}
        	    SMS_FROM: ${SMS_FROM}
        	    GOOGLE_APPLICATION_CREDENTIALS: ${GOOGLE_APPLICATION_CREDENTIALS}
            networks:
              - app-network
            depends_on:
              - db
        ```
        
        - 위에서 작성한 db의 docker-compose.yml 파일에 추가로 작성
        - 포트는 앞의 포트가 EC2의 포트, 뒤의 포트가 컨테이너에서 서버가 할당되는 포트
        - environment에 환경 변수 생성
            - JWT_SECRET : JWT 토큰 생성 시 암호화 키
            - ACCESS_KEY, SECRET_KEY : S3 접근할 IAM 키
            - SMS_KEY, SMS_SECRET, SMS_FROM : 외부 문자 서비스를 위한 키와 발신 번호
            - GOOGLE_APPLICATION_CREDENTIALS : Firebase Admin SDK 사용을 위한 서비스 키 위치
            - beep 프로젝트 폴더에 .env 파일에 환경 변수를 설정하면 docker-compose할 때 자동으로 참조해서 변수들이 들어감
        - 전체 docker-compose.yml 파일
        
        ```bash
        # docker-compose version
        version: "3.8"
        
        #container list
        services:
          db:
            image: mysql:5.7
            container_name: mysql
            restart: always
            volumes:
              # Mount cotainer drive to real drive
              - ./db:/docker-entrypoint-initdb.d
            networks:
              # network inside of a container
              - app-network
            ports:
              - "3306:3306"
            environment:
              MYSQL_ROOT_PASSWORD: ssafyA406
              TZ: "Asia/Seoul"
            privileged: true
        
          redis:
            image: redis
            command: redis-server --port 6379
            container_name: redis
            hostname: redis
            labels:
              - "name=redis"
              - "mode=standalone"
            ports:
              - "6379:6379"
        
          beep:
        	 # 이미지가 있다면
           # image: beep
        	 build: 
        		 - context: ./ 
           container_name: beep
           restart: always
           ports:
             - "8081:8081"
           environment:
             TZ: "Asia/Seoul"
             # Spring application.properties DB
             SPRING_DATASOURCE_URL: "jdbc:mysql://db:3306/BEEP?userUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Seoul&zeroDateTimeBehavior=convertToNull&rewriteBatchedStatements=true"
             server.address: 0.0.0.0
             JWT_SECRET: ${JWT_SECRET}
             ACCESS_KEY: ${ACCESS_KEY}
             SECRET_KEY: ${SECRET_KEY}
             SMS_KEY: ${SMS_KEY}
             SMS_SECRET: ${SMS_SECRET}
             SMS_FROM: ${SMS_FROM}
             GOOGLE_APPLICATION_CREDENTIALS: ${GOOGLE_APPLICATION_CREDENTIALS}
           volumes:
             - /home/ubuntu/beep/key:/home/ubuntu/beep
           networks:
             - app-network
           depends_on:
             - db
        
        networks:
          app-network:
            driver: bridge
        ```
        

## APK 파일 생성

참고 : [https://zinle.tistory.com/519](https://zinle.tistory.com/519)

1. 안드로이드 스튜디오에서 Build - Generate Signed Bundle or APK 선택

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/ffd262f2-d161-4704-a555-74851e23fd08/Untitled.png)

2. APK 를 선택하고 Next

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/cca433a4-ba48-4e45-9391-4b0e465f353b/Untitled.png)

3. Key Store Path - Create New 클릭

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/4c339c78-e6a3-4aca-960d-376f86495cab/Untitled.png)

4. Key 저장 위치 선택

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/cfdb7d77-59c8-41d3-921b-86c776831fe7/Untitled.png)

5. 폴더를 생성 및 선택하고 원하는 파일 이름을 설정 (C드라이브 안쪽에 저장을 권장)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/a21e144c-4d9a-4531-9b96-0bda7983ddef/Untitled.png)

6. 원하는 JKS 비밀번호와 키 비밀번호를 설정 후 Certificate에는 앱의 메타데이터 정보를 넣음

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/f54273ff-fbe3-4e35-aeff-908463133ba9/Untitled.png)

7. Next 클릭 후 Build Variant를 release로 선택

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/77c42318-5735-442e-a26b-ead3c6a0f0d6/Untitled.png)

8. Finish를 누르면 빌드가 시작되고 완료 후에 프로젝트 폴더 안에서 확인 가능

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/1af87a0d-4c66-4774-bd8e-adc903a81a5d/Untitled.png)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/6439a6bb-e508-4175-b3d1-20c2b8666236/Untitled.png)
