package shopping.shop.item.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopping.shop.common.BaseTimeEntity;
import shopping.shop.upload.domian.UploadFile;

import javax.persistence.*;

@Entity
@Getter @NoArgsConstructor
public class Item extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long itemId;

    private String itemName;
    private Integer price;
    private Integer quantity;

    private String userId;

    @Embedded
    private UploadFile attachFile;

    @Builder
    public Item(String itemName, Integer price, Integer quantity, String userId, UploadFile attachFile) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.userId = userId;
        this.attachFile = attachFile;
    }
}
