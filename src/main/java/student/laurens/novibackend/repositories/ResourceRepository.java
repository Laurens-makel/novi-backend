package student.laurens.novibackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import student.laurens.novibackend.entities.AbstractEntity;

@NoRepositoryBean
public interface ResourceRepository<R extends AbstractEntity> extends JpaRepository<R, Integer> {

}
