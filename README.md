# Spring Member Project

## 사용 기술

- Java 21
- Spring Boot
- Spring Data JPA
- H2 Database

# Spring Study - Day JPA

## 오늘 공부한 내용

### JDBC vs JPA

JdbcTemplate

- SQL 직접 작성
- PreparedStatement 사용
- KeyHolder로 PK 받기
- RowMapper로 객체 변환

JPA

- SQL 자동 생성
- 객체 중심 개발
- EntityManager 사용
- 코드량 감소

---

### Entity

- @Entity : JPA가 관리하는 객체
- @Id : 기본키
- @GeneratedValue : PK 자동 생성(H2)

---

### EntityManager

- persist() : 저장
- find() : PK 조회
- createQuery() : 조건 조회

---

### JPQL

```java
select m from Member m
```

- SQL이 아니라 객체(Member)를 조회한다.

---

### 영속성 컨텍스트

- JPA가 관리하는 객체 저장소
- persist() 하면 관리 시작
- find()로 조회한 객체도 관리 대상

---

### Dirty Checking

관리 중인 객체의 값이 변경되면 UPDATE SQL을 자동 생성한다.

---

### Flush

영속성 컨텍스트의 변경 내용을 DB에 반영한다.

---

### Commit

DB 변경을 최종 확정한다.

---

### 오늘 기억할 것

JDBC는 SQL 중심

JPA는 객체 중심

## 학습 내용

### Day1

- JPA 적용
- Entity
- EntityManager
- JPQL

### Day2

- 영속성 컨텍스트
- Dirty Checking
- Flush
- Commit

## 실행 방법

./gradlew bootRun
