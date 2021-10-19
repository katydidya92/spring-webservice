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
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @NotNull
    private String userId;
    @NotNull
    private String userPw;
    @NotNull
    private String name;
    @NotNull
    private int age;
    @NotNull
    private String email;

    private String memberRole;

    @Embedded
    private Address address;

    public Member(String userId, String userPw) {
        this.userId = userId;
        this.userPw = userPw;
    }

    @Builder
    public Member(String userId, String userPw, String name, int age, String email, Address address) {
        this.userId = userId;
        this.userPw = userPw;
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
    }
}
