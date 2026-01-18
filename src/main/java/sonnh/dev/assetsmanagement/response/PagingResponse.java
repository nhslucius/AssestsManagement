package sonnh.dev.assetsmanagement.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PagingResponse<T> {

    private List<T> items;
    private Pagination pagination;

    @Getter
    @Builder
    public static class Pagination {
        private int page;
        private int size;
        private long totalItems;
        private int totalPages;
    }
}