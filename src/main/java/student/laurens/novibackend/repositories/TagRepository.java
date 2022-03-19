package student.laurens.novibackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import student.laurens.novibackend.entities.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    @Query("SELECT t FROM Tag t WHERE t.title = :tagTitle")
    public Tag getTagByTitle(@Param("tagTitle") String tagTitle);
}
