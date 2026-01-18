package sonnh.dev.assetsmanagement.specification;//package sonnh.base.specification;
//
//import org.springframework.data.jpa.domain.Specification;
//
//import jakarta.persistence.criteria.Predicate;
//import java.util.ArrayList;
//import java.util.List;
//
//public class GenericSpecification<T> {
//
//    public Specification<T> build(List<SearchCriteria> criteriaList) {
//        return (root, query, cb) -> {
//
//            List<Predicate> predicates = new ArrayList<>();
//
//            for (SearchCriteria c : criteriaList) {
//                if (c.getValue() == null) continue;
//
//                switch (c.getOperation()) {
//                    case EQUAL ->
//                            predicates.add(cb.equal(root.get(c.getField()), c.getValue()));
//
//                    case LIKE ->
//                            predicates.add(cb.like(
//                                    cb.lower(root.get(c.getField())),
//                                    "%" + c.getValue().toString().toLowerCase() + "%"
//                            ));
//
//                    case GREATER_THAN ->
//                            predicates.add(cb.greaterThan(
//                                    root.get(c.getField()),
//                                    (Comparable) c.getValue()
//                            ));
//
//                    case LESS_THAN ->
//                            predicates.add(cb.lessThan(
//                                    root.get(c.getField()),
//                                    (Comparable) c.getValue()
//                            ));
//
//                    case IN ->
//                            predicates.add(root.get(c.getField()).in(c.getValue()));
//                }
//            }
//
//            return cb.and(predicates.toArray(new Predicate[0]));
//        };
//    }
//}