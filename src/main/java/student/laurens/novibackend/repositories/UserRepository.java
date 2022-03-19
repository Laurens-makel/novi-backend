package student.laurens.novibackend.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import student.laurens.novibackend.entities.User;

/**
 * Repository Interface that exposes database methods for USERS and USER_ROLES tables.
 *
 * This interface is a subtype of CrudRepository defined by Spring Data JPA so Spring will generate implementation class at runtime.
 * We define the getUserByUsername() method annotated by a JPA query to be used by Spring Security for authentication.
 * If you’re new to Spring Data JPA, kind check this quick start guide.
 * https://www.codejava.net/frameworks/spring/understand-spring-data-jpa-with-simple-example
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */

public interface UserRepository extends ResourceRepository<User> {

    @Query("SELECT u FROM User u WHERE u.username = :username")
    public User getUserByUsername(@Param("username") String username);

}
