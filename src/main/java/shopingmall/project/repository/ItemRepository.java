package shopingmall.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopingmall.project.entity.shoping.Item;

import java.util.List;

/**
 * 이름검색, 가격비교(이상,이하로 검색), 설명검색??, 할인중 검색, (옷이나 부품으로 할 경우 제조사?메이커?검색)
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByName(String name);
}
