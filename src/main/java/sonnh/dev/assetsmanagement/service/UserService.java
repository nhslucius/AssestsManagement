package sonnh.dev.assetsmanagement.service;//package sonnh.base.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//import sonnh.base.specification.SearchCriteria;
//import sonnh.base.specification.SearchOperation;
//
//import java.util.List;
//
//import static sonnh.base.specification.UserSpecification.hasRole;
//
//@Service
//@RequiredArgsConstructor
//public class UserService {
//
//    private final UserRepository userRepository;
//
//    public Page<UserEntity> search(UserSearchRequest request, Pageable pageable) {
//
//        List<SearchCriteria> criteria = List.of(
//                new SearchCriteria("name", SearchOperation.LIKE, req.getName()),
//                new SearchCriteria("age", SearchOperation.GREATER_THAN, req.getMinAge())
//        );
//
//        Specification<UserEntity> spec =
//                new GenericSpecification<UserEntity>().build(criteria);
//
//        Specification<UserEntity> spec = Specification
//                .where(nameContains(req.getName()))
//                .and(hasRole(req.getRole()))
//                .and(enumEqual("status", req.getStatus()))
//                .and(betweenDates("createdDate", req.getFrom(), req.getTo()))
//                .and(isTrue("active", req.getActive()));
//
//        return userRepository.findAll(spec, pageable);
//
//    }
//}