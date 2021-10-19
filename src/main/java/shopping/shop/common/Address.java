package shopping.shop.common;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private String zipcode;
    private String roadAddr;
    private String addrDetail;
    private String adEtc;

    @Builder
    public Address(String zipcode, String roadAddr, String addrDetail, String adEtc) {
        this.zipcode = zipcode;
        this.roadAddr = roadAddr;
        this.addrDetail = addrDetail;
        this.adEtc = adEtc;
    }
}
