package shopping.shop.member.domain;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import shopping.shop.common.Address;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class MemberDto {

    @NotBlank
    @NotNull
    private String userId;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @Range(min = 1, max = 150)
    private int age;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String zipcode;
    private String roadAddr;

    @NotBlank(message = "상세 주소를 작성해주세요.")
    private String addrDetail;
    private String adEtc;

    private String auth;

    @Builder
    public MemberDto(Member member) {
        this.userId = member.getUserId();
        this.password = member.getPassword();
        this.name = member.getName();
        this.age = member.getAge();
        this.email = member.getEmail();
        this.zipcode = member.getAddress().getZipcode();
        this.roadAddr = member.getAddress().getRoadAddr();
        this.addrDetail = member.getAddress().getAddrDetail();
        this.adEtc = member.getAddress().getAdEtc();
        this.auth = member.getAuth();
    }

    public Member toEntity() {
        return Member.builder().userId(userId).password(password).name(name).age(age).email(email).address(Address.builder().zipcode(zipcode).roadAddr(roadAddr).addrDetail(addrDetail).adEtc(adEtc).build()).auth(auth).build();
    }
}
