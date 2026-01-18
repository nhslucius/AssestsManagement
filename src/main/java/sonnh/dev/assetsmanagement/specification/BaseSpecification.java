package sonnh.dev.assetsmanagement.specification;//package sonnh.base.specification;
//
//import org.springframework.data.jpa.domain.Specification;
//
//public abstract class BaseSpecification<T> {
//
//    protected Specification<T> hasText(String field, String value) {
//        return (root, query, cb) ->
//                value == null ? null :
//                        cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase() + "%");
//    }
//
//    protected Specification<T> equal(String field, Object value) {
//        return (root, query, cb) ->
//                value == null ? null :
//                        cb.equal(root.get(field), value);
//    }
//
//    protected Specification<T> greaterThan(String field, Comparable value) {
//        return (root, query, cb) ->
//                value == null ? null :
//                        cb.greaterThan(root.get(field), value);
//    }
//
//    protected Specification<T> lessThan(String field, Comparable value) {
//        return (root, query, cb) ->
//                value == null ? null :
//                        cb.lessThan(root.get(field), value);
//    }
//}
