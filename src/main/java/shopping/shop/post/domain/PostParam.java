package shopping.shop.post.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class PostParam {
    private String searchType;
    private String searchValue;

    private String sortType;

    @Builder
    public PostParam(String searchType, String searchValue, String sortType) {
        this.searchType = searchType;
        this.searchValue = searchValue;
        this.sortType = sortType;
    }
}