package shopping.shop.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shopping.shop.comment.domain.Comment;
import shopping.shop.comment.service.CommentRepositoryImpl;
import shopping.shop.domain.MyPageSize;
import shopping.shop.like.service.LikeService;
import shopping.shop.login.session.SessionConst;
import shopping.shop.member.domain.Member;
import shopping.shop.member.domain.MemberDto;
import shopping.shop.post.domain.Post;
import shopping.shop.post.domain.PostDto;
import shopping.shop.post.domain.PostParam;
import shopping.shop.post.service.PostRepositoryImpl;
import shopping.shop.post.service.PostService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final PostService postService;
    private final PostRepositoryImpl postService2;
    private final CommentRepositoryImpl commentService;
    private final LikeService likeService;

    @GetMapping("/")
    public String openAddPost(Model model) {
        model.addAttribute("article", new Post());
        return "boards/addPost";
    }

    @PostMapping("/")
    public String addPost(@Valid @ModelAttribute("article") PostDto postDto, BindingResult bindingResult, RedirectAttributes attributes,
                          @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "boards/addPost";
        }

        Post post = Post.builder()
                .userId(member.getUserId())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();

        Post savedPost = postService.save(post);
        attributes.addAttribute("Id", savedPost.getId());
        attributes.addAttribute("status", true);

        return "redirect:/boards/{Id}";
    }

    @GetMapping("/list")
    public String openList(Pageable pageable, Model model,
                           @RequestParam(required = false, defaultValue = "ALL") String searchType,
                           @RequestParam(required = false, defaultValue = "") String keyword,
                           @RequestParam(required = false, defaultValue = "ALL") String sortType) {

        PostParam param = PostParam.builder()
                .searchType(searchType)
                .searchValue(keyword)
                .sortType(sortType).build();

        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, MyPageSize.PAGE_SIZE);

        Page<Post> map = postService2.findAllByKeyword(param, pageable);

        model.addAttribute("pageSize", MyPageSize.PAGE_SIZE);
        model.addAttribute("posts", map);
        return "boards/list";
    }

    @GetMapping("/{postId}/edit")
    public String openEditPost(@PathVariable Long postId, Model model,
                               @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        Post post = postService.getById(postId);

        PostDto postDto = PostDto.builder()
                .post(post)
                .build();

        model.addAttribute("article", postDto);

        if (member.getUserId() == post.getUserId()) {
            return "boards/editPost";
        }
        return "redirect:/boards/list";
    }

    @PostMapping("/{postId}/edit")
    public String updatePost(@PathVariable Long postId, @ModelAttribute("article") PostDto postDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "boards/editPost";
        }

        log.info("edit title = {}, detail = {}", postDto.getTitle(), postDto.getContent());

        postService.updatePost(postId, postDto.getTitle(), postDto.getContent());

        return "redirect:/boards/{postId}";
    }

    @GetMapping("/{postId}")
    public String openBoard(@PathVariable Long postId, Model model,
                            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {

        Post post = postService.getById(postId);

        postService.updateHitById(postId);
        boolean likeCheck = likeService.isNotAlreadyLike(member, post);

        log.info("boardController : likeCheck= {}", likeCheck);

        List<Comment> cmts = commentService.findAllById(postId);
        List<Comment> reCmt = commentService.findAllRelistById(postId);
        model.addAttribute("article", post);
        model.addAttribute("cmts", cmts);
        model.addAttribute("reCmt", reCmt);
        model.addAttribute("member", member);
        model.addAttribute("likeCheck", likeCheck);

        return "boards/post";
    }

    /**
     * 테스트용 글 추가
     */
    @PostConstruct
    public void initDb() {
        for (int i = 1; i < 101; i++) {
            postService.save(new Post("test!", "a" + i, "b" + i));
        }
    }

}