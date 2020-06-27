# money

## 로컬 환경 구성
### Requirements
- MySQL 5.7
- JDK 8

**MySQL Docker Continaer 실행**
```
sh run-local.sh
```


## Used
- Spring Boot 2.2.8.RELEASE
- Gradle 4+
- JUnit5

## Package Structure
```
homework
└───config 
└───controller
└───datasource
│   └───entity
│   └───repository
└───dto
└───exception
└───filter
└───service
└───util
│   Application.java
```

## DB Entity
![erd](./erderderd.jpg)

## HTTP MEthod
- GET / POST 만 사용하며 모두 동사를 포함하여 사용한다.
