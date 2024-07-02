package web.kursach.hobby.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import web.kursach.hobby.dto.UserCommentDTO;
import web.kursach.hobby.entity.User;
import web.kursach.hobby.service.UserService;

import java.io.Serializable;


@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {
    
    
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        
        if (targetDomainObject == null || !(permission instanceof String)) {
            return false;
        }

        // Проверяем разрешение на редактирование или удаление пользователей
        if ("editUser".equals(permission) || "deleteUser".equals(permission)) {
            if (targetDomainObject instanceof User) {
                User targetUser = (User) targetDomainObject;
                CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();

                // Администратор может редактировать и удалять любого пользователя
                if (currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                    return true;
                }

                //return currentUser.getId() == targetUser.getId();
                return currentUser.getUser().getId() == targetUser.getId();
            }
        }

        // Проверяем разрешение на редактирование или удаление комментариев
        if ("editComment".equals(permission) || "deleteComment".equals(permission)) {
            if (targetDomainObject instanceof UserCommentDTO) {
                UserCommentDTO comment = (UserCommentDTO) targetDomainObject;
                CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();

                // Администратор может редактировать и удалять любой комментарий
                if (currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                    return true;
                }

                // Пользователь может редактировать и удалять только свои комментарии
                return comment.getUserId() == currentUser.getUser().getId();
            }
        }

        return false;
    } 


    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
            Object permission) {
        throw new UnsupportedOperationException();
    }
}