package student.laurens.novibackend.unit.services.impl;

import lombok.Getter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import student.laurens.novibackend.entities.AppUserDetails;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.repositories.UserRepository;
import student.laurens.novibackend.services.AppUserDetailsService;
import student.laurens.novibackend.unit.services.OwnedServiceUnitTestBase;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * Test class to test actions on {@link AppUserDetailsService}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public class AppUserDetailsServiceUnitTest extends OwnedServiceUnitTestBase<User> {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        public AppUserDetailsService service() {
            return new AppUserDetailsService();
        }
    }

    @Autowired
    private @Getter
    AppUserDetailsService service;

    @MockBean
    private @Getter UserRepository repository;

    @Override
    protected User create() {
        return consumer();
    }
    @Override
    protected User create(User user) {
        return user;
    }

    @Before
    public void setup(){
        User bob = createTestUser("Bob", "Doe", "BOB", "myPass123", "USER");
        User john = createTestUser("John", "Doe", "JOHN", "myPass123","USER");
        User alex = createTestUser("Alex", "Doe", "ALEX", "myPass123","USER");

        List<User> users = Arrays.asList(john, bob, alex);

        Mockito.when(repository.findAll()).thenReturn(users);
        Mockito.when(repository.getUserByUsername(bob.getUsername())).thenReturn(bob);
        Mockito.when(repository.getUserByUsername(john.getUsername())).thenReturn(john);
        Mockito.when(repository.getUserByUsername(alex.getUsername())).thenReturn(alex);
    }

    @Test
    public void whenValidUserName_thenUserShouldBeFound() {
        String username = "BOB";

        AppUserDetails found = (AppUserDetails) service.loadUserByUsername(username);

        assertThat(found.getUsername()).isEqualTo(username);
        verifyFindByUsernameIsCalledOnce(username);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void whenInvalidUserName_thenUserShouldNotBeFound_Exception() {
        String username = "UNKNOWN";

        AppUserDetails found = (AppUserDetails) service.loadUserByUsername(username);
    }

    @Test
    public void given3Users_whengetAll_thenReturn3Records() {
        User bob = createTestUser("Bob", "Doe", "BOB", "myPass123", "USER");
        User john = createTestUser("John", "Doe", "JOHN", "myPass123", "USER");
        User alex = createTestUser("Alex", "Doe", "ALEX", "myPass123", "USER");

        Iterable<User> users = service.getResources();
        verifyFindAllUsersIsCalledOnce();

        assertThat(users).hasSize(3).extracting(User::getUsername).contains(alex.getUsername(), john.getUsername(), bob.getUsername());
    }

    @Test
    public void whenAddingUser_ThenRepositorySaveUserIsCalled() {
        User peter = createTestUser("Peter", "Doe", "PETER", "myPass123", "USER");

        service.createResource(peter);
        verifySaveUserIsCalledOnce(peter);
    }

    private void verifyFindByUsernameIsCalledOnce(String name) {
        Mockito.verify(repository, VerificationModeFactory.times(1)).getUserByUsername(name);
        Mockito.reset(repository);
    }

    private void verifyFindAllUsersIsCalledOnce() {
        Mockito.verify(repository, VerificationModeFactory.times(1)).findAll();
        Mockito.reset(repository);
    }

    private void verifySaveUserIsCalledOnce(User user) {
        Mockito.verify(repository, VerificationModeFactory.times(1)).save(user);
        Mockito.reset(repository);
    }

}
