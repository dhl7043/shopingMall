package shopingmall.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopingmall.project.entity.shoping.Member;
import shopingmall.project.dto.request.MemberCreate;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    List<Member> findByName(String name);

    Optional<Member> findByEmail(String email);
}
