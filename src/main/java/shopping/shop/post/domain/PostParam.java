package shopping.shop.post.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class PostParam {
    private String searchType;
    private String value;

    public PostParam(String searchType, String value) {
        this.searchType = searchType;
        this.value = value;
    }
}