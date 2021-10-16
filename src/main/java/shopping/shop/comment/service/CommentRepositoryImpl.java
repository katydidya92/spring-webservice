package shopping.shop.comment.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.shop.comment.domain.CmtListResponseDto;
import shopping.shop.comment.domain.Comment;
import shopping.shop.comment.repository.CommentRepositoryCustom;
import shopping.shop.domain.IsAvailable;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<CmtListResponseDto> findAllById(Long postId) {
        List<Comment> cmtList = query.selectFrom(comment)
                .where(postIdEq(postId))
                .where(isAvailableEq(IsAvailable.IsAvailable))
                .orderBy(comment.commentId.desc())
                .fetch();

        return cmtList.stream().map(CmtListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<CmtListResponseDto> findAllRelistById(Long postId) {
        List<Comment> reCmtList = query.selectFrom(comment)
                .where(postIdEq(postId))
                .where(isNotAvailableEq(IsAvailable.IsAvailable))
                .orderBy(comment.commentId.desc())
                .fetch();

        return reCmtList.stream().map(CmtListResponseDto::new)
                .collect(Collectors.toList());
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
        return isEmpty(postId) ? null : comment.postId.eq(postId);
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
