package shopping.shop.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping.shop.common.Address;
import shopping.shop.common.BaseTimeEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Member extends BaseTimeEntity{

    @Id @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "userId", unique = true)
    private String userId;

    @NotNull
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "age")
    private int age;

    @NotNull
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "auth")
    private String auth;

    @Embedded
    private Address address;

    @Builder
    public Member(Long id, String userId, String password, String name, int age, String email, String auth, Address address) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.age = age;
        this.email = email;
        this.auth = auth;
        this.address = address;
    }
}
