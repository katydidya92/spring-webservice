package shopping.shop.comment.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.shop.comment.domain.CmtListResponseDto;
import shopping.shop.comment.domain.CmtSaveRequestDto;
import shopping.shop.comment.domain.Comment;
import shopping.shop.comment.repository.CommentRepositoryCustom;

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
                .leftJoin(comment.parent)
                .where(postIdEq(postId))
                .orderBy(comment.parent.cmtId.asc().nullsFirst(),
                        comment.lastModifiedDate.asc())
                .fetch();

        return cmtList.stream().map(CmtListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateComment(CmtSaveRequestDto cmt, Long cmtId) {
        query.update(comment)
                .set(comment.cmtContent, cmt.getCmtContent())
                .where(cmtIdEq(cmtId))
                .execute();
    }

    @Override
    public void deleteComment(Long cmtId) {
        query.delete(comment)
                .where(cmtIdEq(cmtId))
                .execute();
    }

    private BooleanExpression postIdEq(Long postId) {
        return isEmpty(postId) ? null : comment.postId.eq(postId);
    }

    private BooleanExpression cmtIdEq(Long cmtId) {
        return isEmpty(cmtId) ? null : comment.cmtId.eq(cmtId);
    }

}
