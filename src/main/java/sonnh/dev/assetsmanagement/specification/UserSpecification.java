package sonnh.dev.assetsmanagement.specification;//package sonnh.base.specification;
//
//import org.springframework.data.jpa.domain.Specification;
//
//import java.time.LocalDate;
//
//public class UserSpecification extends GenericSpecification<UserEntity> {
//
//    public Specification<UserEntity> build(UserSearchRequest req) {
//
//        return Specification
//                .where(hasText("name", req.getName()))
//                .and(hasText("email", req.getEmail()))
//                .and(greaterThan("age", req.getMinAge()))
//                .and(lessThan("age", req.getMaxAge()));
//    }
//
//    public static Specification<UserEntity> hasRole(String roleName) {
//        return (root, query, cb) -> {
//            if (roleName == null) return null;
//
//            return cb.equal(
//                    root.join("roles").get("name"),
//                    roleName
//            );
//        };
//    }
//
//    public static Specification<UserEntity> distinctExample(String roleName) {
//        return (root, query, cb) -> {
//            query.distinct(true);
//            return cb.equal(root.join("roles").get("name"), roleName);
//        };
//
//    }
//
//    public static Specification<UserEntity> hasExpensiveOrder(BigDecimal amount) {
//        return (root, query, cb) -> {
//
//            var subQuery = query.subquery(Long.class);
//            var orderRoot = subQuery.from(OrderEntity.class);
//
//            subQuery.select(cb.literal(1L))
//                    .where(
//                            cb.equal(orderRoot.get("user"), root),
//                            cb.greaterThan(orderRoot.get("totalAmount"), amount)
//                    );
//
//            return cb.exists(subQuery);
//        };
//    }
//
//    public static <T> Specification<T> betweenDates(
//            String field,
//            LocalDate from,
//            LocalDate to
//    ) {
//        return (root, query, cb) -> {
//            if (from == null && to == null) return null;
//
//            if (from != null && to != null) {
//                return cb.between(root.get(field), from, to);
//            }
//
//            if (from != null) {
//                return cb.greaterThanOrEqualTo(root.get(field), from);
//            }
//
//            return cb.lessThanOrEqualTo(root.get(field), to);
//        };
//    }
//
//    public static <T, E extends Enum<E>>
//    Specification<T> enumEqual(String field, E value) {
//        return (root, query, cb) ->
//                value == null ? null :
//                        cb.equal(root.get(field), value);
//    }
//
//    public static <T>
//    Specification<T> isTrue(String field, Boolean value) {
//        return (root, query, cb) ->
//                value == null ? null :
//                        cb.equal(root.get(field), value);
//    }
//
//}