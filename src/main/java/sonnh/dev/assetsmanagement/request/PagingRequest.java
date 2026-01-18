package sonnh.dev.assetsmanagement.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingRequest {
    private Integer page = 0;
    private Integer size = 20;
}