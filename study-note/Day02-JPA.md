# Day02 - JPA 연관관계

## 오늘 배운 내용

### 1. @ManyToOne

- 회원 여러 명은 하나의 팀에 속한다.
- Member가 연관관계의 주인이다.
- Member 테이블에 team_id(FK)가 생성된다.

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "team_id")
private Team team;

2. @OneToMany
@OneToMany(mappedBy = "team")
private List<Member> members = new ArrayList<>();
Team 하나는 여러 Member를 가진다.
mappedBy는 Member의 team이 관계를 관리한다는 의미이다.

3. 연관관계의 주인

외래키(FK)가 있는 쪽이 연관관계의 주인이다.

Member → Team

Member가 team_id를 관리한다.

4. 편의 메서드
public void changeTeam(Team team){

    if(this.team != null){
        this.team.getMembers().remove(this);
    }

    this.team = team;
    team.getMembers().add(this);
}

왜?

객체 상태를 항상 동일하게 유지하기 위해

5. Lazy Loading
@ManyToOne(fetch = FetchType.LAZY)
Team은 실제 사용할 때 조회된다.

6. Proxy

Member 조회

↓

Proxy Team 생성

↓

team.getName()

↓

실제 Team 조회

7. N + 1

회원조회 1번

↓

회원 100명

↓

팀조회 100번

↓

총 101번 SQL

8. Fetch Join
select m from Member m join fetch m.team

Member와 Team을 한 번에 조회한다.

N+1 문제를 해결할 수 있다.
```
