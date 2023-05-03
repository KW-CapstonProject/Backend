package capstonServer.capstonServer.service;



import capstonServer.capstonServer.entity.User;
import capstonServer.capstonServer.repository.UsersRepository;
import capstonServer.capstonServer.security.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = usersRepository.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with userId : " + username)
                );

        return CustomUserPrincipal.create(user,user.getName());
    }


//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return usersRepository.findByEmail(username)
//                .map(this::createUserDetails)
//                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
//    }
//
//    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
//    private UserDetails createUserDetails(User user) {
//        System.out.println("정보"+user.getUsername());
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
//    }


}
