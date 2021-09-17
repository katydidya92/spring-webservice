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
import shopping.shop.post.repository.PostRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Post save(Post post) {
        return postRepository.save(post);
    }

    public Post getById(Long postId) {
        return postRepository.getById(postId);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Page<Post> findAll(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, MyPageSize.PAGE_SIZE);
        return postRepository.findAll(pageable);
    }

    @Transactional
    public void updatePost(Long postId, String title, String content) {
        Post post = postRepository.getById(postId);

        post.setTitle(title);
        post.setContent(content);
    }

    public Page<Post> search(String title, String content, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, MyPageSize.PAGE_SIZE);
        return postRepository.findByTitleContainingOrContentContaining(title, content, pageable);
    }

    @Transactional
    public int updateHitById(Long id) {
        return postRepository.updateHitById(id);
    }

    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Transactional
    public void updatePostIsAvailable(Long postId) {
        postRepository.updatePostIsAvailable(postId);
    }
}
