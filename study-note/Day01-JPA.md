# Spring Study - JPA 기초

> 날짜: 2026-08-02

## 오늘 구현한 내용

- Spring Data JPA 의존성 추가
- `Member` 클래스를 JPA 엔티티로 변경
- `JpaMemberRepository` 구현
- 회원 저장
- 이름으로 회원 조회
- 회원 전체 조회

---

## 1. JdbcTemplate과 JPA 차이

### JdbcTemplate

JdbcTemplate에서는 개발자가 직접 처리해야 한다.

- SQL 작성
- `PreparedStatement` 생성
- `?`에 값 설정
- `KeyHolder`로 DB가 생성한 PK 받기
- `RowMapper`로 DB 결과를 객체로 변환

### JPA

JPA에서는 다음 한 줄로 회원을 저장할 수 있다.

```java
em.persist(member);
```

JPA가 내부적으로 처리하는 것:

- INSERT SQL 생성
- SQL 실행
- H2가 생성한 PK 받기
- `Member` 객체에 PK 반영

### 핵심

> JdbcTemplate은 SQL 중심이고, JPA는 객체 중심이다.

---

## 2. Entity 설정

```java
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
```

- `@Entity`: JPA가 관리할 엔티티라는 표시
- `@Id`: 기본키(PK) 지정
- `@GeneratedValue`: H2가 ID를 자동 생성

---

## 3. EntityManager

`EntityManager`는 엔티티를 저장하고 조회하는 JPA의 핵심 객체다.

```java
em.persist(member);
```

- 객체 저장
- 영속성 컨텍스트가 객체 관리 시작

```java
em.find(Member.class, id);
```

- PK로 조회
- 조회한 객체도 영속성 컨텍스트가 관리

```java
em.createQuery(...);
```

- 이름처럼 PK가 아닌 조건으로 조회할 때 사용

---

## 4. JpaMemberRepository

### 저장

```java
@Override
public Member save(Member member) {
    em.persist(member);
    return member;
}
```

### 이름으로 조회

```java
@Override
public Optional<Member> findByName(String name) {
    List<Member> result = em.createQuery(
            "select m from Member m where m.name = :name",
            Member.class
        )
        .setParameter("name", name)
        .getResultList();

    return result.stream().findAny();
}
```

- `:name`: 값을 넣을 이름 있는 자리
- `setParameter("name", name)`: `:name`에 매개변수 값을 넣음
- `getResultList()`: 결과가 여러 개일 수 있으므로 List로 조회

### 전체 조회

```java
@Override
public List<Member> findAll() {
    return em.createQuery(
            "select m from Member m",
            Member.class
        )
        .getResultList();
}
```

JPQL은 테이블 이름이 아니라 엔티티 클래스와 필드를 사용한다.

---

## 5. 트랜잭션

```java
@Transactional
```

여러 DB 작업을 하나로 묶는다.

- 성공: Commit
- 실패: Rollback

JPA의 저장과 변경 감지는 보통 트랜잭션 안에서 동작한다.

---

## 6. 영속성 컨텍스트

영속성 컨텍스트는 JPA가 엔티티를 관리하는 공간이다.

```text
new Member()
→ 일반 자바 객체

em.persist(member)
→ 영속성 컨텍스트가 관리하는 영속 객체
```

`em.find()`로 조회한 객체도 영속 객체다.

---

## 7. 변경 감지

```java
Member member = em.find(Member.class, 1L);
member.setName("홍길동");
```

별도의 `update()`를 호출하지 않아도 JPA가 변경을 감지해 UPDATE SQL을 생성한다.

이를 **Dirty Checking(변경 감지)**이라고 한다.

---

## 8. Flush와 Commit

### Flush

영속성 컨텍스트의 변경 내용을 SQL로 만들어 DB에 전달한다.

### Commit

DB에 전달된 변경을 최종 확정한다.

```text
객체 변경
→ 변경 감지
→ Flush
→ SQL 전송
→ Commit
```

Flush 후 오류가 발생해도 Commit 전이면 Rollback할 수 있다.

---

## 9. 1차 캐시

```java
Member member1 = em.find(Member.class, 1L);
Member member2 = em.find(Member.class, 1L);
```

같은 트랜잭션과 영속성 컨텍스트 안에서는:

```java
member1 == member2
```

결과가 `true`다.

첫 번째 조회 결과를 영속성 컨텍스트에 보관하고, 두 번째 조회에서는 같은 객체를 반환하기 때문이다.

---

## 오늘 핵심 정리

- JPA는 반복적인 JDBC 코드를 줄여준다.
- JPA는 테이블보다 객체 중심으로 동작한다.
- `persist()`는 객체를 저장하고 관리하기 시작한다.
- `find()`는 PK로 조회한다.
- 조건 조회에는 JPQL과 `createQuery()`를 사용한다.
- 영속 객체는 변경 감지 대상이다.
- Flush는 SQL 전송, Commit은 최종 확정이다.
- 같은 PK를 다시 조회하면 1차 캐시의 같은 객체를 반환한다.
