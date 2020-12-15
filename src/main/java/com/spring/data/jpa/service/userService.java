package com.spring.data.jpa.service;

import com.spring.data.jpa.models.dao.IUsuarioDao;
import com.spring.data.jpa.models.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class userService implements UserDetailsService {

    @Autowired
    private IUsuarioDao usuarioDao;



    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

            Usuario usuario =   usuarioDao.findByCliente(s);
        List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("Admin"));
        return new User(usuario.getCliente(),usuario.getContrasena(),true,true,true,true,authorities);

    }
}
