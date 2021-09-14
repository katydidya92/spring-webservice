package shopping.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.shop.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
