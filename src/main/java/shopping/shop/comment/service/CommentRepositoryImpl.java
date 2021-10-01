package shopping.shop.comment.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.shop.comment.domain.Comment;
import shopping.shop.comment.repository.CommentRepositoryCustom;
import shopping.shop.domain.IsAvailable;

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
                .where(isAvailableEq(IsAvailable.IsAvailable))
                .fetch();
    }

    @Override
    public List<Comment> findAllRelistById(Long postId) {
        return query.selectFrom(comment)
                .where(postIdEq(postId))
                .where(isNotAvailableEq(IsAvailable.IsAvailable))
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

    @Override
    public void deleteComment(Long cmtId) {
        long execute = query
                .delete(comment)
                .where(cmtIdEq(cmtId))
                .execute();
    }

    private BooleanExpression postIdEq(Long postId) {
        return isEmpty(postId) ? null : comment.post.id.eq(postId);
    }

    private BooleanExpression cmtIdEq(Long cmtId) {
        return isEmpty(cmtId) ? null : comment.commentId.eq(cmtId);
    }

    private BooleanExpression isAvailableEq(Integer cmtReplyNumIsZero) {
        return isEmpty(cmtReplyNumIsZero) ? null : comment.cmtReplyId.eq(Long.valueOf(IsAvailable.IsAvailable));
    }
    private BooleanExpression isNotAvailableEq(Integer cmtReplyNumIsZero) {
        return isEmpty(cmtReplyNumIsZero) ? null : comment.cmtReplyId.ne(Long.valueOf(IsAvailable.IsAvailable));
    }
}
