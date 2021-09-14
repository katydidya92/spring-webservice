package shopping.shop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private String zipcode;
    private String roadAddr;
    private String addrDetail;
    private String adEtc;

    public Address(String zipcode, String roadAddr, String addrDetail, String adEtc) {
        this.zipcode = zipcode;
        this.roadAddr = roadAddr;
        this.addrDetail = addrDetail;
        this.adEtc = adEtc;
    }
}
