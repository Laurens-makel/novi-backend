package student.laurens.novibackend.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import student.laurens.novibackend.users.AppUserDetails;
import student.laurens.novibackend.users.AppUserDetailsService;
import student.laurens.novibackend.users.User;
import student.laurens.novibackend.users.UserRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-integration-test.properties")
public class AppUserDetailsServiceIntegrationTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        public AppUserDetailsService service() {
            return new AppUserDetailsService();
        }
    }

    @Autowired
    private AppUserDetailsService service;

    @MockBean
    private UserRepository repository;

    @Before
    public void setup(){
        User bob = createTestUser("Bob", "Doe", "BOB", "myPass123");
        User john = createTestUser("John", "Doe", "JOHN", "myPass123");
        User alex = createTestUser("Alex", "Doe", "ALEX", "myPass123");

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
        User bob = createTestUser("Bob", "Doe", "BOB", "myPass123");
        User john = createTestUser("John", "Doe", "JOHN", "myPass123");
        User alex = createTestUser("Alex", "Doe", "ALEX", "myPass123");

        Iterable<User> users = service.listAll();
        verifyFindAllUsersIsCalledOnce();

        assertThat(users).hasSize(3).extracting(User::getUsername).contains(alex.getUsername(), john.getUsername(), bob.getUsername());
    }

    @Test
    public void whenAddingUser_ThenRepositorySaveUserIsCalled() {
        User peter = createTestUser("Peter", "Doe", "PETER", "myPass123");

        service.addUser(peter);
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

    private User createTestUser(String firstname, String lastname, String username, String password){
        User testUser = new User();

        testUser.setFirstName(firstname);
        testUser.setLastName(lastname);
        testUser.setUsername(username);
        testUser.setPassword(password);

        return testUser;
    }
}