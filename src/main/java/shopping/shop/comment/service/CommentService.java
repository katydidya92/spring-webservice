package shopping.shop.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.shop.comment.domain.CmtResponseDto;
import shopping.shop.comment.domain.Comment;
import shopping.shop.comment.domain.CmtSaveRequestDto;
import shopping.shop.comment.repository.CommentRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Long commentWrite(CmtSaveRequestDto comment) {
        return commentRepository.save(comment.toEntity()).getCommentId();
    }

    @Transactional
    public void cmtReplyWrite(Comment comment) {
        commentRepository.save(comment);
    }

    public Comment getById(Long cmtId) {
        return commentRepository.getById(cmtId);
    }

    public CmtResponseDto findById(Long cmtId) {
        Comment entity = commentRepository.findById(cmtId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id : " + cmtId));
        return new CmtResponseDto(entity);
    }
}
