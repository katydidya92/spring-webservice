package shopping.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.shop.domain.Item;
import shopping.shop.dto.ItemDto;
import shopping.shop.repository.ItemRepository2;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService2 {

    private final ItemRepository2 itemRepository;

    @Transactional
    public void updateItem(Long itemId, ItemDto updateItemParam) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setItemName(updateItemParam.getItemName());
        findItem.setPrice(updateItemParam.getPrice());
        findItem.setQuantity(updateItemParam.getQuantity());
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
