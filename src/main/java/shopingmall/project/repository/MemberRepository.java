package shopingmall.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopingmall.project.dto.MemberDto;
import shopingmall.project.entity.shoping.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<MemberDto> findByName(String name);
}
