package com.monousoooo.example.security.util;

import cn.hutool.core.util.StrUtil;
import com.monousoooo.example.security.service.MoUser;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@UtilityClass
public class SecurityUtils {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public MoUser getUser(Authentication authentication){
        Object principal = authentication.getPrincipal();
        if (principal instanceof MoUser) {
            return (MoUser) principal;
        }
        return null;
    }

    public MoUser getUser(){
        Authentication authentication = getAuthentication();
        return getUser(authentication);
    }

    public List<Long> getRoles() {
        Authentication authentication = getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<Long> roleIds = new ArrayList<>();
        authorities.stream()
                .filter(granted -> StrUtil.startWith(granted.getAuthority(), "ROLE_"))
                .forEach(granted -> {
                    String id = StrUtil.removePrefix(granted.getAuthority(), "ROLE_");
                    roleIds.add(Long.parseLong(id));
                });
        return roleIds;
    }
}
