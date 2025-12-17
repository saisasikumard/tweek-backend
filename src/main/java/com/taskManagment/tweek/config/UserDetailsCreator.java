package com.taskManagment.tweek.config;

import com.taskManagment.tweek.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsCreator  implements UserDetails {
    String userName;
    String password;
    List<GrantedAuthority> authorities;
    public UserDetailsCreator(Users user) {
        this.userName=user.getUsername();
        this.password= user.getPassword();
        //for role null check
        String roles = user.getRole() != null ? user.getRole() : "ROLE_USER";
        String rolesAList[] =roles.split(",");//"has_User,has_Admin"

        List<GrantedAuthority> grantedAuthorities=new ArrayList<>();
        for(String role:rolesAList){
            SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(role);
            grantedAuthorities.add(simpleGrantedAuthority);
        }
        this.authorities=grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
