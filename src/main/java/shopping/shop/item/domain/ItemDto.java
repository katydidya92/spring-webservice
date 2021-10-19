package shopping.shop.item.domain;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;
import shopping.shop.upload.domian.UploadFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter @Setter
@RequiredArgsConstructor
public class ItemDto {

    private Long itemId;

    private String userId;

    @NotBlank
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;

    @NotNull
    @Max(value = 9999)
    private Integer quantity;

    private MultipartFile attachFile;

    @Builder
    public ItemDto(Long itemId, String userId, String itemName, Integer price, Integer quantity, MultipartFile attachFile) {
        this.itemId = itemId;
        this.userId = userId;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.attachFile = attachFile;
    }
}
