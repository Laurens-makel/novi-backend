package student.laurens.novibackend.unit.dto.mappers;

import org.junit.Test;
import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.entities.dto.ResourceDto;
import student.laurens.novibackend.entities.dto.mappers.ResourceMapper;

import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public abstract class ResourceMapperTest <R extends AbstractEntity, D extends ResourceDto> {

    protected abstract ResourceMapper<R, D> getMapper();
    protected abstract D dto();
    protected abstract R entity();

    @Test
    public void testToEntity() {
        D dto = dto();

        R entity = getMapper().toEntity(dto);

        assertToEntity(dto, entity);
    }
    protected abstract void assertToEntity(D dto, R entity);

    @Test
    public void testToDto() {
        R entity = entity();

        D dto = getMapper().toDto(entity);

        assertToDto(entity, dto);
    }
    protected abstract void assertToDto(R entity, D dto);


    protected <T extends AbstractEntity> void assertDtoSetMatchesEntitySet(Set<Integer> dtoSet, Set<T> entitySet){
        for(Integer dtoId : dtoSet){
            Optional<T> optional = entitySet
                    .stream()
                    .filter( item -> item.getId().equals(dtoId))
                    .findAny();

            assertTrue(optional.isPresent());
        }
    }

    protected <T extends AbstractEntity> void assertEntitySetMatchesDtoSet(Set<T> entitySet, Set<Integer> dtoSet){
        for(T item : entitySet){
            Optional<Integer> optional = dtoSet
                    .stream()
                    .filter( dtoId -> dtoId.equals(item.getId()))
                    .findAny();

            assertTrue(optional.isPresent());
        }
    }
}
