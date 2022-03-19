package student.laurens.novibackend.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import student.laurens.novibackend.controllers.UserRestController;
import student.laurens.novibackend.repositories.RoleRepository;
import student.laurens.novibackend.entities.*;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class to test actions on {@link RoleService}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public class RoleServiceIntegrationTest extends ServiceIntegrationTestBase {

    @TestConfiguration
    static class RoleServiceTestContextConfiguration {
        @Bean
        public RoleService service() {
            return new RoleService();
        }
    }

    @Autowired
    private RoleService service;

    @MockBean
    private RoleRepository repository;

    @Before
    public void setup(){
        Role admin = createRole("ADMIN");
        Role user = createRole("USER");

        List<Role> users = Arrays.asList(admin, user);

        Mockito.when(repository.findAll()).thenReturn(users);
        Mockito.when(repository.getRoleByName(admin.getName())).thenReturn(admin);
        Mockito.when(repository.getRoleByName(user.getName())).thenReturn(user);
    }

    @Test
    public void whenValidRoleName_thenRoleShouldBeFound() {
        String rolename = "ADMIN";

        Role found = service.getResource(rolename);

        assertThat(found.getName()).isEqualTo(rolename);
        verifyFindByRoleNameIsCalledOnce(rolename);
    }

    private void verifyFindByRoleNameIsCalledOnce(String rolename) {
        Mockito.verify(repository, VerificationModeFactory.times(1)).getRoleByName(rolename);
        Mockito.reset(repository);
    }

    private Role createRole(String rolename){
        Role role = new Role();
        role.setName(rolename);

        return role;
    }
}
