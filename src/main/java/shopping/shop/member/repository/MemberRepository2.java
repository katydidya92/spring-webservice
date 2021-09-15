package shopping.shop.member.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import shopping.shop.member.domain.Member;
import shopping.shop.member.domain.MemberDto;

import javax.persistence.EntityManager;
import java.util.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberRepository2 {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public List<Member> findById(String userId) {
        log.info("findById : userId = {}" , userId);
        return em.createQuery("select m from Member m where m.userId = :userId", Member.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<MemberDto> findAll2() {
        return em.createQuery("select m from Member m", MemberDto.class).getResultList();
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }


}
