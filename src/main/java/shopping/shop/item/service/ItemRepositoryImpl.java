package shopping.shop.item.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.shop.item.domain.ItemDto;
import shopping.shop.item.repository.ItemRepositoryCustom;

import javax.persistence.EntityManager;

import static org.springframework.util.ObjectUtils.isEmpty;
import static shopping.shop.item.domain.QItem.item;

@Service
@Transactional(readOnly = true)
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory query;

    public ItemRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override @Transactional
    public void updateItem(Long itemId, ItemDto updateItemParam) {
        query.update(item)
                .set(item.itemName, updateItemParam.getItemName())
                .set(item.price, updateItemParam.getPrice())
                .set(item.quantity, updateItemParam.getQuantity())
                .where(itemIdEq(itemId))
                .execute();
    }

    private BooleanExpression itemIdEq(Long itemId) {
        return isEmpty(itemId) ? null : item.itemId.eq(itemId);
    }
}
