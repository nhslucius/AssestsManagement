package sonnh.dev.assetsmanagement.specification;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchCriteria {
    private String field;
    private SearchOperation operation;
    private Object value;
}
