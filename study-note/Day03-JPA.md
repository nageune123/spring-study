# Spring Study - Day 3

## 오늘 공부한 내용

### 1. Spring Data JPA

- JpaRepository 사용
- save(), findById(), findAll(), delete()
- JpaRepository는 내부적으로 EntityManager를 사용한다.
- findById()는 Optional<Member>를 반환한다.
- 메서드 이름만으로 조회 가능
  - findByName()
  - findByNameAndAge()
  - findByNameContaining()

---

### 2. Spring 구조

브라우저
→ Controller
→ Service
→ Repository
→ JpaRepository
→ EntityManager
→ 영속성 컨텍스트
→ DB

- Controller : 요청을 받는다.
- Service : 비즈니스 로직 처리
- Repository : DB 접근

---

### 3. Spring Core

#### Bean

- Spring이 생성하고 관리하는 객체

#### IoC

- 객체 생성과 관리 권한이 Spring으로 넘어간 것

#### DI

- Spring이 필요한 Bean을 자동으로 주입

---

### 4. 생성자 주입(Constructor Injection)

```java
private final MemberService memberService;

public MemberController(MemberService memberService) {
    this.memberService = memberService;
}
```

- 생성자를 통해 Bean을 전달받는다.
- final 사용 가능
- 객체 변경 방지
- 실무에서 가장 많이 사용하는 방식

---

### 5. this

```java
this.name = name;
```

- 왼쪽(this.name) : 멤버 필드
- 오른쪽(name) : 생성자 매개변수
- 전달받은 값을 필드에 저장한다.

---

## 오늘 느낀 점

- JpaRepository가 EntityManager를 대신 사용하는 구조 이해
- Bean, IoC, DI 연결 이해
- 생성자 주입과 this의 의미 이해
- Spring 전체 흐름 이해

Controller
→ Service
→ Repository
→ JpaRepository
→ EntityManager
→ 영속성 컨텍스트
→ DB
