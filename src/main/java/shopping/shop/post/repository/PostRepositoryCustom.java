package shopping.shop.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shopping.shop.post.domain.Post;
import shopping.shop.post.domain.PostParam;

import java.util.List;

public interface PostRepositoryCustom {

    Page<Post> findAllByKeyword(PostParam param, Pageable pageable);

    List<Post> findAllByKeyword(PostParam param);
}
