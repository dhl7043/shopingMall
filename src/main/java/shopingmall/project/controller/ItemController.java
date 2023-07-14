package shopingmall.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shopingmall.project.config.UserPrincipal;
import shopingmall.project.dto.request.ItemCreate;
import shopingmall.project.dto.request.ItemEdit;
import shopingmall.project.dto.request.ItemSearchCondition;
import shopingmall.project.dto.response.ItemResponse;
import shopingmall.project.service.ItemService;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    /* 상품 등록 */
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/items/save")
    public void saveItem(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid ItemCreate request) {
        itemService.saveItem(userPrincipal.getUserId(), request);
    }

    /* 상품 한개 */
    @GetMapping("/items/{itemId}")
    public ItemResponse getItem(@PathVariable Long itemId) {
        return itemService.findByItemId(itemId);
    }

    /* 상품 모두다 */
    @GetMapping("/items")
    public Page<ItemResponse> items(ItemSearchCondition condition, Pageable pageable) {
        return itemService.searchItems(condition, pageable);
    }

    /* 상품 수정 */
    @PreAuthorize("hasRole('CUSTOMER')")
    @PatchMapping("/items/{itemId}")
    public void editItem(@PathVariable Long itemId, @RequestBody @Valid ItemEdit request) {
        itemService.updateItem(itemId, request);
    }

    /* 상품 삭제 */
    @PreAuthorize("hasRole('CUSTOMER') && hasPermission(#memberId, 'POST', 'DELETE')")
    @DeleteMapping("/items/{itemId}")
    public void deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
    }
}
