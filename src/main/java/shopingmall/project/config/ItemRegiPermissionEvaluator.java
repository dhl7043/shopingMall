package shopingmall.project.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import shopingmall.project.entity.shoping.Item;
import shopingmall.project.exception.NotFoundItemException;
import shopingmall.project.repository.ItemRepository;

import java.io.Serializable;

@Slf4j
@RequiredArgsConstructor
public class ItemRegiPermissionEvaluator implements PermissionEvaluator {

    private final ItemRepository itemRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    /**
     * authentication - 인증된 사용자
     * targetId - post에서 온 id (게시글id)
     *
     * 403 -> accessDenied 권한없음
     * 404 -> 게시글없음 - 보안적으로 더 괜찮음
     */
    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Item item = itemRepository.findById((Long) targetId)
                .orElseThrow(NotFoundItemException::new);

        if (!item.getMember().getId().equals(userPrincipal.getUserId())) { // member랑 매핑해야할듯 맴버가 가지고있는 id랑 대칭
            log.error("[인가실패] 해당 사용자가 작성한 글이 아닙니다. targetId={}", targetId);
            return false;
        }

        return true;
    }
}
