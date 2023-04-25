package shopingmall.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopingmall.project.entity.shoping.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
