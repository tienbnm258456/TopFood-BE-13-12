package com.demo.shop.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.demo.shop.entity.User;

public class UsuarioPrincipalFactory {

    public static UsuarioPrincipal build(User usuario){
        List<GrantedAuthority> authorities =
                usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol.getName())).collect(Collectors.toList());
        return new UsuarioPrincipal(usuario.getEmail(), usuario.getPassword(), authorities);
    }
}
