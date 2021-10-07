package shopping.shop.item.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import shopping.shop.item.domain.Item;
import shopping.shop.item.repository.ItemRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Commit
@SpringBootTest
class ItemRepositoryImplTest {

    @Autowired ItemRepository itemRepository;
    @Autowired ItemRepositoryImpl itemService;

    @Test @Transactional
    public void itemAdd() {
        Integer price = 100;
        Item item = Item.builder()
                .itemName("item")
                .price(price)
                .quantity(price)
                .userId("test!")
                .build();

        Item saveItem = itemRepository.save(item);

        assertThat(saveItem.getItemName()).isEqualTo("item");
    }


}