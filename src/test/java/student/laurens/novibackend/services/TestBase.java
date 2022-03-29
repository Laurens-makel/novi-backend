package student.laurens.novibackend.services;

import org.junit.After;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.entities.Role;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.repositories.ResourceRepository;

import java.util.Date;

/**
 * Base class to provide default methods for testing Service.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-integration-test.properties")
public abstract class TestBase <R extends AbstractEntity> {
    protected final String USER = "DefaultUser";
    protected final String USER_ROLE = "USER";

    protected final String ADMIN = "DefaultAdmin";
    protected final String ADMIN_ROLE = "ADMIN";

    protected final String CONTENT_CREATOR = "DefaultContentCreator";
    protected final String CONTENT_CREATOR_ROLE = "CONTENT_CREATOR";

    protected final String MODERATOR = "DefaultModerator";
    protected final String MODERATOR_ROLE = "MODERATOR";

    @After
    public void base_breakdown(){
        Mockito.reset(getRepository());
    }

    abstract protected R create();
    abstract protected BaseService<R> getService();
    abstract protected ResourceRepository<R> getRepository();

    protected void verifyExistsByIdIsCalledOnce(Integer roleId) {
        Mockito.verify(getRepository(), VerificationModeFactory.times(1)).existsById(roleId);
    }
    protected void verifyDeleteByIdIsCalledOnce(Integer roleId) {
        Mockito.verify(getRepository(), VerificationModeFactory.times(1)).deleteById(roleId);
    }
    protected void verifySaveIsCalledOnce(R resource) {
        Mockito.verify(getRepository(), VerificationModeFactory.times(1)).save(resource);
    }
    protected void verifyGetOneIsCalledOnce(Integer resource) {
        Mockito.verify(getRepository(), VerificationModeFactory.times(1)).getOne(resource);
    }

    protected User consumer(){
        return createTestUser("Joe", "Biden", "SleepyJoe123", "GoBrandon", "USER");
    }
    protected User consumer(Role role){
        User consumer = createTestUser("Joe", "Biden", "SleepyJoe123", "GoBrandon", "USER");
        consumer.getRoles().clear();
        consumer.getRoles().add(role);
        return consumer;
    }
    protected String unique(String text){
        return text + new Date().getTime();
    }

    protected User createDefaultUser(){
        return createTestUser("Bob", "Doe", USER, "MyPassword123", "USER");
    }
    protected User createDefaultAdmin(){
        return createTestUser("John", "Doe", ADMIN, "MyPassword123", "ADMIN");
    }
    protected User createDefaultContentCreator(){
        return createTestUser("Kanye", "West", CONTENT_CREATOR, "MyPassword123", "CONTENT_CREATOR");
    }
    protected User createUniqueContentCreator(){
        return createTestUser("Kanye", "West", unique(CONTENT_CREATOR), "MyPassword123", "CONTENT_CREATOR");
    }
    protected User createDefaultModerator(){
        return createTestUser("Kanye", "West", MODERATOR, "MyPassword123", "MODERATOR");
    }
    protected User createTestUser(String firstname, String lastname, String username, String password,  String role){
        User testUser = new User();

        testUser.setFirstName(firstname);
        testUser.setLastName(lastname);
        testUser.setUsername(username);
        testUser.setPassword(password);
        testUser.getRoles().add(createRole(role));

        return testUser;
    }
    protected Role createRole(String rolename){
        Role role = new Role();
        role.setName(rolename);
        role.setId(generateId());

        return role;
    }
    protected int generateId(){
        return (int) (Math.random() * (100 - 1)) + 1;
    }
    protected void mockResourceExistsById(R resource){
        Mockito.when(getRepository().existsById(resource.getId())).thenReturn(true);
    }
    protected void mockResourceGetById(R resource){
        Mockito.when(getRepository().getOne(resource.getId())).thenReturn(resource);
    }

}
