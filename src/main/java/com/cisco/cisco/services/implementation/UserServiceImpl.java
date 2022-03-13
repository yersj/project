package com.cisco.cisco.services.implementation;
import com.cisco.cisco.entities.AuthRole;
import com.cisco.cisco.entities.Course;
import com.cisco.cisco.entities.User;
import com.cisco.cisco.repositories.CourseRepository;
import com.cisco.cisco.repositories.RoleRepository;
import com.cisco.cisco.repositories.UserRepository;
import com.cisco.cisco.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class UserServiceImpl implements UserDetailsService, UserService {


    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  RoleRepository roleRepository;

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user =userRepository.findByEmail(email);
        if(user!=null){
            return user;
        }else{
            throw new UsernameNotFoundException("User Not Found");
        }


    }

//    public List<User> getAllStudentsByCourseId(Long id){
//        return userRepository.findAllByCourseId(id);
//    }
    public User register(User user){

        AuthRole role= roleRepository.findByRole("ROLE_STUDENT");
        if(role!=null){
            List<AuthRole> roles=new ArrayList<>();
            roles.add(role);
            user.setRoles(roles);
            userRepository.save(user);
            return user;
        }
        return null;
    }

    public User getUser(Long id){
        return userRepository.findById(id).orElse(null);
    }


    public void saveUser(User user){
        userRepository.save(user);
    }
}
