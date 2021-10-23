package shopping.shop.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shopping.shop.member.domain.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m.userId from Member m where userId = :userId")
    List<Member> findUserId(@Param("userId") String userId);

    Optional<Member> findByUserId(String userId);

}
