package student.laurens.novibackend.services;

import lombok.Getter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
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
public class RoleServiceIntegrationTest extends ServiceIntegrationTestBase<Role> {

    @TestConfiguration
    static class RoleServiceTestContextConfiguration {
        @Bean
        public RoleService service() {
            return new RoleService();
        }
    }

    @Autowired
    private @Getter RoleService service;

    @MockBean
    private @Getter RoleRepository repository;

    @Override
    protected Role create() {
        return createRole("test role");
    }

    @Before
    public void setup(){
        Role admin = createRole("ADMIN");
        Role user = createRole("USER");

        List<Role> users = Arrays.asList(admin, user);

        Mockito.when(repository.findAll()).thenReturn(users);
        Mockito.when(repository.getRoleByName(admin.getName())).thenReturn(admin);
        Mockito.when(repository.getRoleByName(user.getName())).thenReturn(user);
    }

    @After
    public void breakdown(){
        Mockito.reset(repository);
    }

    @Test
    public void whenValidRoleName_thenRoleShouldBeFound() {
        // Given
        Role admin = createRole("ADMIN");
        Mockito.when(repository.getRoleByName(admin.getName())).thenReturn(admin);

        // When
        Role found = service.getResource(admin.getName());

        // Then
        assertThat(found.getName()).isEqualTo(admin.getName());
        verifyFindByRoleNameIsCalledOnce(admin.getName());
    }

    @Test
    public void whenValidRoleId_thenRoleShouldBeFound() {
        // Given
        User consumer = consumer();
        Role admin = createRole("ADMIN");
        Mockito.when(repository.getOne(admin.getId())).thenReturn(admin);

        // When
        Role found = service.getResourceById(admin.getId(), consumer);

        // Then
        assertThat(found.getName()).isEqualTo(admin.getName());
        verifyGetOneIsCalledOnce(admin.getId());
    }

    @Test
    public void createRoleTest() {
        // given
        Role role = createRole("new role");

        // when
        service.createResource(role);

        // then
        verifySaveIsCalledOnce(role);
    }

    @Test
    public void updateRoleTest() {
        // Given
        User consumer = consumer();
        Role role = createRole("to be updated");
        Mockito.when(repository.existsById(role.getId())).thenReturn(true);

        // when
        service.updateResourceById(role.getId(), role, consumer);

        // then
        verifyExistsByIdIsCalledOnce(role.getId());
        verifySaveIsCalledOnce(role);
    }

    @Test
    public void deleteRoleTest() {
        // Given
        User consumer = consumer();
        Role role = createRole("to be deleted");
        Mockito.when(repository.existsById(role.getId())).thenReturn(true);

        // when
        service.deleteResourceById(role.getId(), consumer);

        // then
        verifyExistsByIdIsCalledOnce(role.getId());
        verifyDeleteByIdIsCalledOnce(role.getId());
    }

    private void verifyFindByRoleNameIsCalledOnce(String rolename) {
        Mockito.verify(repository, VerificationModeFactory.times(1)).getRoleByName(rolename);
    }

    protected Role createRole(String rolename){
        Role role = new Role();
        role.setName(rolename);
        role.setId(generateId());

        return role;
    }


}
