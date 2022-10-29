package com.springsecurity.bootsecurity.service;



import com.springsecurity.bootsecurity.model.Role;
import com.springsecurity.bootsecurity.model.User;
import com.springsecurity.bootsecurity.repository.RolesRepository;
import com.springsecurity.bootsecurity.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImp(UsersRepository usersRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }


//    @Override
//    public void add(User user, List<String> roles) {
//        List<Role> role = new ArrayList();
//        for (String rol :roles){
//            if (rol.equals("ADMIN")){
//                role.add(new Role(user.getUsername(),"ROLE_ADMIN" ));
//            }
//            if (rol.equals("USER")){
//                role.add(new Role(user.getUsername(),"ROLE_USER" ));
//            }
//        }
//
//       // Role role1 = new Role(user.getUsername(), "ROLE_USER") ;
//       // role.add(role1);
//        user.setRoles(role);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        usersRepository.save(user);
//    }
@Override
public void add(User user, String role) {
    List<Role> roles = new ArrayList();
    Role newRole = new Role(user.getUsername(), role) ;
    roles.add(newRole);
    user.setRoles(roles);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    usersRepository.save(user);
}

    @Override
    public void update(User user, List<String> rolesNames) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
        List<Role> roles = new ArrayList();
        for (String roleName : rolesNames){
            roles.add(new Role(user.getUsername(), roleName));
        }
        user.setRoles(roles);
    }


    @Override
    public void update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }
    @Override
    public List<User> listUsers() {
        return usersRepository.findAll();
    }

    @Override
    public List<String> listRoles() {
        List<Role> roleList = rolesRepository.findAll();
        List<String> roleNames = new ArrayList<>();
        for (Role role : roleList){
           roleNames.add(role.getRole());
        }
        return roleNames;
    }

    @Override
    public List<String> userListRole(String username) {

        List<Role> roleList = rolesRepository.findAllById(Collections.singleton((username)));
        List<String> roleNames = new ArrayList<>();
        for (Role role : roleList){
            roleNames.add(role.getRole());
        }
        return roleNames;
    }


    @Override
    public User findById(int id) {
        Optional<User> user = usersRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public List<String> findRole(List<String> shortNames) {
        List<String> roleList = new ArrayList<>();
        for (String role : listRoles()) {
            if (shortNames.contains(role)){
                roleList.add(role);
            }
        }
        return roleList;
    }


    @Override
    public void delete(int id) {
        usersRepository.deleteById(id);
    }


    @Override
    public User findByUsername(String username) {
        Optional<User> user = usersRepository.findByUsername(username);
        return user.orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByUsername(username);
        if (user.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }
}
