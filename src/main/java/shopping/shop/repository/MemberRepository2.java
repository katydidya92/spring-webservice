package shopping.shop.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shopping.shop.domain.Member;
import shopping.shop.dto.MemberDto;

import javax.persistence.EntityManager;
import java.util.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberRepository2 {

    private final EntityManager em;

    public void save(Member member) {
        log.info("save={}", member);
        em.persist(member);
    }

    public List<Member> findById(String userId) {
        log.info("findById : userId = {}" , userId);
        return em.createQuery("select m from Member m where m.userId = :userId", Member.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public Optional<Member> findByLoginId(String loginId) {
        log.info("MemberRepository : findByLoginId = {}", loginId);
        return findAll().stream()
                .filter(m -> m.getUserId().equals(loginId))
                .findFirst();
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<MemberDto> findAll2() {
        return em.createQuery("select m from Member m", MemberDto.class).getResultList();
    }

    public Member findOne(Long id) {
        Member member = em.find(Member.class, id);
        return member;
    }


}
