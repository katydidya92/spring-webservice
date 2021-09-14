package shopping.shop.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class MemberDto {

    @NotBlank @NotNull
    private String userId;

    @NotNull @NotBlank
    private String userPw;

    @NotNull @NotBlank
    private String name;

    @NotNull @Range(min = 1, max = 150)
    private int age;

    @NotNull @NotBlank @Email
    private String email;

    @NotBlank
    private String zipcode;
    private String roadAddr;

    @NotBlank(message = "상세 주소를 작성해주세요.")
    private String addrDetail;
    private String adEtc;

    public MemberDto(String userId, String userPw, String name, int age, String email) {
        this.userId = userId;
        this.userPw = userPw;
        this.name = name;
        this.age = age;
        this.email = email;
    }
}
