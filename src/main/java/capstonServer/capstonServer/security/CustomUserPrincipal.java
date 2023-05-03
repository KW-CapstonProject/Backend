package capstonServer.capstonServer.security;

import capstonServer.capstonServer.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class CustomUserPrincipal implements UserDetails {
    private String email;
    private String password;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;

    private Map<String, Object> attributes;
    private User user;

    public User getUser() {
        return user;
    }

    public CustomUserPrincipal(String email, String password, Collection<? extends GrantedAuthority> authorities, User user, String name) {
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.user = user;
        this.username=name;
    }

    public static CustomUserPrincipal create(User user, String name) {
        List<GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        return new CustomUserPrincipal(
                user.getEmail(),
                user.getPassword(),
                authorities,
                user,
                name
        );
    }

//    public static CustomUserPrincipal create(User user, Map<String, Object> attributes) {
//        CustomUserPrincipal userPrincipal = CustomUserPrincipal.create(user);
//        userPrincipal.setAttributes(attributes);
//        return userPrincipal;
//    }

    public String getEmail() {
        return email;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


}
