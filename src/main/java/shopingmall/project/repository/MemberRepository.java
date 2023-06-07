package shopingmall.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopingmall.project.entity.shoping.Member;
import shopingmall.project.dto.request.MemberCreate;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    List<MemberCreate> findByName(String name);
}
