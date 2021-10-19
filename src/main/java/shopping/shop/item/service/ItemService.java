package shopping.shop.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttribute;
import shopping.shop.item.domain.Item;
import shopping.shop.item.domain.ItemDto;
import shopping.shop.item.domain.ItemListResponseDto;
import shopping.shop.item.domain.ItemResponseDto;
import shopping.shop.item.repository.ItemRepository;
import shopping.shop.login.session.SessionConst;
import shopping.shop.member.domain.Member;
import shopping.shop.upload.file.FileStore;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final FileStore fileStore;

    public ItemResponseDto addItem(ItemDto itemDto,
                                   @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) throws IOException {

        Item item = itemRepository.save(
                Item.builder()
                        .itemName(itemDto.getItemName())
                        .userId(member.getUserId())
                        .quantity(itemDto.getQuantity())
                        .price(itemDto.getPrice())
                        .attachFile(fileStore.storeFile(itemDto.getAttachFile()))
                        .build()
        );

        return ItemResponseDto.builder().entity(item).build();
    }

    public List<ItemListResponseDto> itemList() {
        List<Item> items = itemRepository.findAll();
        return items.stream().map(ItemListResponseDto::new).collect(Collectors.toList());
    }

    public ItemResponseDto itemOne(Long itemId) {
        Item item = itemRepository.getById(itemId);
        return ItemResponseDto.builder().entity(item).build();
    }
}
