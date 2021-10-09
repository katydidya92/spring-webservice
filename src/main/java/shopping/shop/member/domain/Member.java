package shopping.shop.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping.shop.domain.Address;
import shopping.shop.domain.BaseTimeEntity;
import shopping.shop.comment.domain.Comment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

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

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Comment> comments;

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
