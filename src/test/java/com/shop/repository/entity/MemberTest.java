package com.shop.repository.entity;

import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Log4j2
public class MemberTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("Auditing 테스트")
    @WithMockUser(username="gildong" , roles="USER")        //test 환경에 임시로 권한부여
    @Commit
    public void auditingTest(){
        Member Member = new Member();
        memberRepository.save(Member);

        em.flush();
        em.clear();

        Member member = memberRepository.findById(Member.getId())
                .orElseThrow(EntityExistsException::new);

        log.info("register time : " + member.getRegTime());
        log.info("update time : " + member.getUpdateTime());
        log.info("create member : " + member.getCreatedBy());
        log.info("modify member : " + member.getModifiedBy());
    }
}
