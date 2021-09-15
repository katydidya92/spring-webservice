package shopping.shop.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.shop.item.domain.Item;
import shopping.shop.item.domain.ItemDto;
import shopping.shop.item.repository.ItemRepository2;

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
