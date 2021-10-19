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
import shopping.shop.comment.domain.CmtListResponseDto;
import shopping.shop.comment.service.CommentRepositoryImpl;
import shopping.shop.common.MyPageSize;
import shopping.shop.like.service.LikeService;
import shopping.shop.login.session.SessionConst;
import shopping.shop.member.domain.Member;
import shopping.shop.post.domain.Post;
import shopping.shop.post.domain.PostSaveRequestDto;
import shopping.shop.post.domain.PostParam;
import shopping.shop.post.service.PostRepositoryImpl;
import shopping.shop.post.service.PostService;

import javax.annotation.PostConstruct;
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

    @GetMapping("/form")
    public String openPost(@RequestParam(required = false) Long id, Model model, RedirectAttributes attributes,
                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        if (id == null) {
            model.addAttribute("article", new Post());
        } else {
            Post post = postService.getById(id);

            if (member.getUserId() == post.getUserId()) {
                PostSaveRequestDto postSaveRequestDto =
                        PostSaveRequestDto.builder()
                                .title(post.getTitle())
                                .content(post.getContent())
                                .userId(post.getUserId())
                                .build();
                model.addAttribute("article", postSaveRequestDto);
            } else {
                attributes.addAttribute("Id", id);
                return "redirect:/boards/{Id}";
            }
        }
        return "boards/writePost";
    }

    @PostMapping("/form")
    public String addPost(@RequestParam(required = false) Long id,
                          @Valid @ModelAttribute("article") PostSaveRequestDto dto, BindingResult bindingResult, RedirectAttributes attributes,
                          @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "boards/writePost";
        }

        if (id == null) {
            PostSaveRequestDto postSaveRequestDto =
                    PostSaveRequestDto.builder()
                            .userId(member.getUserId())
                            .title(dto.getTitle())
                            .content(dto.getContent())
                            .build();

            Long postId = postService.save(postSaveRequestDto);
            attributes.addAttribute("Id", postId);
            attributes.addAttribute("status", true);
        } else {
            postService.updatePost(id, dto);
            attributes.addAttribute("Id", id);
        }
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

    @GetMapping("/{postId}")
    public String openBoard(@PathVariable Long postId, Model model,
                            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {

        Post post = postService.getById(postId);

        postService.updateHitById(postId);
        boolean likeCheck = likeService.isNotAlreadyLike(member, post);

        log.info("boardController : likeCheck= {}", likeCheck);

        List<CmtListResponseDto> cmts = commentService.findAllById(postId);
        model.addAttribute("article", post);
        model.addAttribute("cmts", cmts);
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
            postService.save(PostSaveRequestDto.builder().
                    userId("test!")
                    .title("a" + i)
                    .content("b" + i).build());
        }
    }

}