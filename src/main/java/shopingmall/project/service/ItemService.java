package shopingmall.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopingmall.project.dto.request.ItemSearchCondition;
import shopingmall.project.entity.shoping.Item;
import shopingmall.project.entity.shoping.Member;
import shopingmall.project.exception.NotFoundItemException;
import shopingmall.project.exception.NotFoundMemberException;
import shopingmall.project.repository.ItemRepository;
import shopingmall.project.dto.request.ItemCreate;
import shopingmall.project.dto.request.ItemEdit;
import shopingmall.project.dto.response.ItemResponse;
import shopingmall.project.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    /**
     * 상품등록
     */
    @Transactional
    public Long saveItem(Long memberId, ItemCreate itemCreate) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);

        Item item = Item.builder()
                .name(itemCreate.getName())
                .price(itemCreate.getPrice())
                .itemType(itemCreate.getItemType())
                .description(itemCreate.getDescription())
                .stockQuantity(itemCreate.getStockQuantity())
                .member(member)
                .build();

        Item savedItem = itemRepository.save(item);
        return savedItem.getId();
    }

    /**
     * 상품 수정
     */
    @Transactional
    public void updateItem(Long id, ItemEdit itemEdit) {
        Item item = itemRepository.findById(id)
                .orElseThrow(NotFoundItemException::new);

        item.updateItem(itemEdit.getName(),
                itemEdit.getPrice(),
                itemEdit.getItemType(),
                itemEdit.getDescription(),
                itemEdit.getStockQuantity());

        itemRepository.save(item);
    }

    /**
     * 상품 단건 조회 (상품 하나 클릭시)
     */
    public ItemResponse findByItemId(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(NotFoundItemException::new);

        return item.findOneItem();
    }

    /**
     * 상품 여러개 조회 (조건검색)
     */
    public Page<ItemResponse> searchItems(ItemSearchCondition condition, Pageable pageable) {
        return itemRepository.itemSearchPageComplex(condition, pageable);
    }

    /**
     * 상품 삭제
     */
    public void deleteItem(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(NotFoundItemException::new);

        itemRepository.delete(item);
    }
}
