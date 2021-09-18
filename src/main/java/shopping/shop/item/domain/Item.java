package shopping.shop.item.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import shopping.shop.domain.BaseTimeEntity;
import shopping.shop.member.domain.Member;

import javax.persistence.*;

@Entity
@Getter @NoArgsConstructor
public class Item extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long itemId;

    private String userId;

    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item(String itemName, Integer price, Integer quantity, String userId) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.userId = userId;
    }
}
