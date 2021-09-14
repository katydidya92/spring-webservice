package shopping.shop.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shopping.shop.domain.Item;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository2 {

    private final EntityManager em;

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
