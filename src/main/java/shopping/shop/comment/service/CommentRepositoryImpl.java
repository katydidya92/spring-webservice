package shopping.shop.comment.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.shop.comment.domain.Comment;
import shopping.shop.comment.repository.CommentRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;
import static shopping.shop.comment.domain.QComment.comment;

@Service
@Transactional(readOnly = true)
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory query;

    public CommentRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<Comment> findAllById(Long postId) {
        return query.selectFrom(comment)
                .where(postIdEq(postId))
                .fetch();
    }

    @Override
    @Transactional
    public void updateComment(Comment cmt, Long cmtId) {
        long execute = query
                .update(comment)
                .set(comment.cmtContent, cmt.getCmtContent())
                .where(cmtIdEq(cmtId))
                .execute();
    }

    private BooleanExpression postIdEq(Long postId) {
        return isEmpty(postId) ? null : comment.post.id.eq(postId);
    }

    private BooleanExpression cmtIdEq(Long cmtId) {
        return isEmpty(cmtId) ? null : comment.commentId.eq(cmtId);
    }

}
