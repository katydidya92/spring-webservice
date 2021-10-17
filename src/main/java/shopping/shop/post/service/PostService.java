package shopping.shop.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.shop.domain.MyPageSize;
import shopping.shop.post.domain.Post;
import shopping.shop.post.domain.PostSaveRequestDto;
import shopping.shop.post.repository.PostRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long save(PostSaveRequestDto postSaveRequestDto) {
        return postRepository.save(postSaveRequestDto.toEntity()).getId();
    }

    public Post getById(Long postId) {
        return postRepository.getById(postId);
    }

    @Transactional
    public void updatePost(Long postId, PostSaveRequestDto postSaveRequestDto) {
        Post post = postRepository.getById(postId);
        post.setTitle(postSaveRequestDto.getTitle()+"(수정)");
        post.setContent(postSaveRequestDto.getContent());

    }

    @Transactional
    public int updateHitById(Long id) {
        return postRepository.updateHitById(id);
    }

    @Transactional
    public void updatePostIsAvailable(Long postId) {
        postRepository.updatePostIsAvailable(postId);
    }

}
