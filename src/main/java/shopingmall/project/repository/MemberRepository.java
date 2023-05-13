package shopingmall.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopingmall.project.dto.MemberDto;
import shopingmall.project.entity.shoping.Member;
import shopingmall.project.request.MemberCreate;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<MemberCreate> findByName(String name);
}
