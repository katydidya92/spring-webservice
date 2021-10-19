package shopping.shop.item.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import shopping.shop.upload.domian.UploadFile;
import shopping.shop.upload.file.FileStore;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ItemResponseDto {

    private Long itemId;
    private String userId;
    private String itemName;
    private Integer price;
    private Integer quantity;
    private UploadFile attachFile;
    private LocalDateTime modifiedDate;

    @Builder
    public ItemResponseDto(Item entity) {
        this.itemId = entity.getItemId();
        this.userId = entity.getUserId();
        this.itemName = entity.getItemName();
        this.price = entity.getPrice();
        this.quantity = entity.getQuantity();
        this.attachFile = entity.getAttachFile();
        this.modifiedDate = entity.getLastModifiedDate();
    }
}
