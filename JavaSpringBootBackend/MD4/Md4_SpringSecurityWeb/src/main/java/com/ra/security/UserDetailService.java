package com.ra.security;
//Class này triển khai interface UserDetailsService của Spring Security.
//
//👉 Nhiệm vụ chính:
//
//Khi người dùng đăng nhập, Spring Security sẽ gọi loadUserByUsername() để tải thông tin người dùng từ database dựa trên username.
//
//Sau đó, phương thức này chuyển entity User trong database → thành UserPrinciple, vì Spring Security chỉ hiểu được UserDetails, không làm việc trực tiếp với entity của bạn.

import com.ra.model.entity.Role;
import com.ra.model.entity.User;
import com.ra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // muc dich la tra ve thogn tin user cho userprinciple - > can repository - > tra ve user - > va vi
        // can tra ve doi tuong UserDetail ( thi chugn ta lai co userPrinciple la iplm cua UserDetail
        User user = userRepository.findUserByUsername(username); //- > lay ve 1 user
        UserPrinciple userPrinciple = new UserPrinciple(); // toa doi tuogn de sao chep
        // return userPrinciple; // can tra ve UserDetail - nhung chung ta co userPrinciple la imp cua no nen co the tra ve
        // nhung chung ta khogn tra ve nhu tren vi can truyen user vao ben trong -> dung phuong thuc setUser
        userPrinciple.setUser(user);
        // tra ve the nay van chua du vi chung ta con tra ve các quyền trong userPrinciple
        // trong khởi tạo User - chúng ta có tạo ManyToMAny (fetch = Fetch . EAGER) - > tức là khi lấy về đợc user thì ta có thể lấy được lisstRole -> cái này cần coi lại

        // nhưng hiện tại setAuthorities này có vấn đề - chúng ta set cái gì ?
        // xem Note_setAuthorTrongUserDetailService



        // cahc 1 Chuyển đổi Role -> GrantedAuthority
//        Set<GrantedAuthority> authorities = new HashSet<>();
//        for (Role role : user.getRoles()) {
//            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
//        }
//        userPrinciple.setAuthorities(authorities);

        //cach 2
        userPrinciple.setAuthorities(user.getRoles().stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toSet()));


        return userPrinciple;


    }
}
