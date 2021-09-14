package shopping.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shopping.shop.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where userId = :userId")
    String findUserId(@Param("userId") String userId);
}
