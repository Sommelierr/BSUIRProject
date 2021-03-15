package by.sommelierr.service;

import by.sommelierr.models.Role;
import by.sommelierr.models.User;
import by.sommelierr.repository.RoleRepository;
import by.sommelierr.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    Date date;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if (user == null || user.getStatus().equals("Blocked")) {
            throw new UsernameNotFoundException("User not found");
        }
        this.date = new Date();
        return user;
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepo.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRegistrationDate(new Date());
        user.setStatus("Active");
        userRepo.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepo.findById(userId).isPresent()) {
            userRepo.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<Role> roles(){
        return roleRepository.findAll();
    }

    public void deleteUsers(List<Long> list){
        for (int i = 0; i < list.toArray().length; i++) {
            deleteUser(list.get(i));
        }
    }

    public void blockUsers(List<Long> list){
        for (int i = 0; i < list.toArray().length; i++) {
            Optional <User> user = userRepo.findById(list.get(i));//.orElseThrow();
            user.get().getRoles().clear();
            user.get().getRoles().add(new Role(2L, "ROLE_ANON"));
            user.get().setStatus("Blocked");
            userRepo.save(user.get());
        }
    }

    public void unblockUsers(List<Long> list){
        for (int i = 0; i < list.toArray().length; i++) {
            Optional<User> user = userRepo.findById(list.get(i));//.orElseThrow();
            user.get().getRoles().clear();
            user.get().getRoles().add(new Role(1L, "ROLE_USER"));
            user.get().setStatus("Active");
            userRepo.save(user.get());
        }
    }

    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    public boolean isBlocked(){
        User user = userRepo.findByUsername(getCurrentUsername());
        if(user == null) return true;
        return user.getStatus().equals("Blocked");
    }

    public void doAction(String action, List<Long> idList){
        if(action.equals("delete")) deleteUsers(idList);
        if(action.equals("block")) blockUsers(idList);
        if(action.equals("unblock")) unblockUsers(idList);
    }


    public boolean isNotExecutable(){
        if(!userRepo.existsByUsername(getCurrentUsername()) || isBlocked())
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        return !userRepo.existsByUsername(getCurrentUsername()) || isBlocked();
    }

    public void setLoginDate(){
        String name = getCurrentUsername();
        User user = userRepo.findByUsername(name);
        user.setLoginDate(date);
        userRepo.save(user);
    }

    public void getByDate(){

    }
}
