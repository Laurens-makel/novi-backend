package student.laurens.novibackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.entities.User;

/**
 * Repository Interface that exposes database methods for BLOGPOSTS table.
 *
 * This interface is a subtype of CrudRepository defined by Spring Data JPA so Spring will generate implementation class at runtime.
 * We define the getUserByUsername() method annotated by a JPA query to be used by Spring Security for authentication.
 * If you’re new to Spring Data JPA, kind check this quick start guide.
 * https://www.codejava.net/frameworks/spring/understand-spring-data-jpa-with-simple-example
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public interface BlogpostRepository extends ResourceRepository<Blogpost> {

    @Query("SELECT b FROM Blogpost b WHERE b.published = true")
    Iterable<Blogpost> findAllPublished();

    @Query("SELECT b FROM Blogpost b WHERE b.title = :title")
    Blogpost findByTitle(String title);
}
