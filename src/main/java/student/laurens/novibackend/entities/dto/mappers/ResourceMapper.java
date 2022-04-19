package student.laurens.novibackend.entities.dto.mappers;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.entities.dto.ResourceDto;

import java.lang.reflect.ParameterizedType;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ResourceMapper<R extends AbstractEntity, D extends ResourceDto> {
    protected final ModelMapper mapper = new ModelMapper();
    private TypeMap<R, D> typeMapperToDto;
    private TypeMap<D, R> typeMapperToEntity;

    protected final Converter<Set<AbstractEntity>, Set<Integer>> entitiesToIdentifiers = mappingContext ->
            mappingContext
                    .getSource()
                    .stream()
                    .map(entity -> entity.getId())
                    .collect(Collectors.toSet());

    protected final Converter<Integer, User> authorEntityMapping = mappingContext -> {
        Integer id = mappingContext.getSource();
        User user = new User();
        user.setId(id);
        return user;
    };

    abstract protected TypeMap<D,R> populateToEntityTypeMap(final TypeMap<D, R> typeMapper);
    private TypeMap<D, R> getTypeMapperToEntity(){
        if(typeMapperToEntity == null){
            typeMapperToEntity = populateToEntityTypeMap(mapper.createTypeMap(getDtoClass(), getResourceClass()));
        }
        return typeMapperToEntity;
    }
    public R toEntity(final D dto){
        return getTypeMapperToEntity().map(dto);
    }

    abstract protected TypeMap<R,D> populateToDtoTypeMap(final TypeMap<R, D> typeMapper);
    private TypeMap<R,D> getTypeMapperToDto(){
        if(typeMapperToDto == null){
            typeMapperToDto = populateToDtoTypeMap(mapper.createTypeMap(getResourceClass(), getDtoClass()));
        }
        return typeMapperToDto;
    }
    public D toDto(final R entity){
        return getTypeMapperToDto().map(entity);
    }

    private Class<D> getDtoClass(){
        return (Class<D>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[1];
    }

    private Class<R> getResourceClass(){
        return (Class<R>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

}
