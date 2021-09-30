package shopping.shop.post.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.shop.post.domain.Post;
import shopping.shop.post.domain.PostIsAvailable;
import shopping.shop.post.domain.PostParam;
import shopping.shop.post.repository.PostRepositoryCustom;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;
import static shopping.shop.post.domain.QPost.post;

@Service
@Transactional(readOnly = true)
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory query;

    public PostRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<Post> findAllByKeyword(PostParam param, Pageable pageable) {

        JPAQuery<Post> results = findAllPostQuery(param, pageable);
        QueryResults<Post> queryResults = results.fetchResults();

        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }

    @Override
    public List<Post> findAllByKeyword(PostParam param) {

        JPAQuery<Post> results = findAllPostQuery(param, null);
        return results.fetchResults().getResults();
    }

    private JPAQuery<Post> findAllPostQuery(PostParam param, Pageable pageable) {

        JPAQuery<Post> searchQuery = query
                .selectFrom(post)
                .where(isAvailableEq(PostIsAvailable.postIsAvailable));

        BooleanBuilder bb = new BooleanBuilder();

        // searchType & searchValue
        switch (param.getSearchType()) {
            case "ALL":
                bb.and(post.title.contains(param.getSearchValue())
                        .or(post.content.contains(param.getSearchValue()))
                        .or(post.userId.contains(param.getSearchValue())));
                break;
            case "TITLE":
                bb.and(post.title.contains(param.getSearchValue()));
                break;
            case "CONTENT":
                bb.and(post.content.contains(param.getSearchValue()));
                break;
            case "WRITER":
                bb.and(post.userId.contains(param.getSearchValue()));
                break;
        }

        searchQuery.limit(pageable.getPageSize());
        searchQuery.offset(pageable.getOffset());

        OrderSpecifier<LocalDateTime> dateAsc = post.createdDate.asc();
        OrderSpecifier<Long> idDesc = post.id.desc();
        OrderSpecifier<Integer> countDesc = post.count.desc();

        switch (param.getSortType()) {
            case "ALL":
                searchQuery.orderBy(idDesc);
                break;
            case "HITS":
                searchQuery.orderBy(countDesc);
                break;
            case "OLD":
                searchQuery.orderBy(dateAsc);
                break;
        }
        return searchQuery.distinct().where(bb);

    }

    private BooleanExpression isAvailableEq(Integer postIsAvailable) {
        return isEmpty(postIsAvailable) ? null : post.isAvailable.eq(PostIsAvailable.postIsAvailable);
    }

}
