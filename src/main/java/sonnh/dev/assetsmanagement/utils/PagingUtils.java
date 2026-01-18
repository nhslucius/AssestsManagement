package sonnh.dev.assetsmanagement.utils;//package sonnh.base.utils;
//
//import org.springframework.data.domain.Page;
//import sonnh.base.response.PagingResponse;
//
//public final class PagingUtils {
//
//    private PagingUtils() {}
//
//    public static <T> PagingResponse<T> from(Page<T> page) {
//
//        return PagingResponse.<T>builder()
//                .items(page.getContent())
//                .pagination(PagingResponse.Pagination.builder()
//                        .page(page.getNumber())
//                        .size(page.getSize())
//                        .totalItems(page.getTotalElements())
//                        .totalPages(page.getTotalPages())
//                        .build())
//                .build();
//    }
//}
