package shopping.shop.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.shop.item.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
