package by.sommelierr.test;
import by.sommelierr.models.Role;
import by.sommelierr.models.User;
import by.sommelierr.repository.RoleRepository;
import by.sommelierr.repository.UserRepository;
import by.sommelierr.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

//    List<Role> r;
//
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void test() throws Exception {
        Role role = new Role(3L,"ROLE_YE");
        roleRepository.save(role);
        Assert.assertNotNull(roleRepository.findByName("ROLE_YE"));
    }
}
