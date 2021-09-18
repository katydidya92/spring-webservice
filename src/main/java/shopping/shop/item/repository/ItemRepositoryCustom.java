package shopping.shop.item.repository;

import shopping.shop.item.domain.ItemDto;

public interface ItemRepositoryCustom {

    void updateItem(Long itemId, ItemDto updateItemParam);

}
