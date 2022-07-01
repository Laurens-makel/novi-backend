package student.laurens.novibackend.entities.dto.mappers;

import org.modelmapper.Converter;
import org.modelmapper.TypeMap;
import student.laurens.novibackend.entities.Role;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.entities.dto.UserDto;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper extends ResourceMapper<User, UserDto> {

    @Override
    public TypeMap<UserDto, User> populateToEntityTypeMap(TypeMap<UserDto, User> typeMapper) {
        Converter<Set<Integer>, Set<Role>> roleObjects = mappingContext ->
                mappingContext
                        .getSource()
                        .stream()
                        .map(roleId -> {
                            Role r = new Role();
                            r.setId(roleId);
                            return r;
                        })
                        .collect(Collectors.toSet());

        typeMapper.addMappings(mapper -> mapper
                .using(roleObjects)
                .map(
                        UserDto::getRoles,
                        User::setRoles
                )
        );
        return typeMapper;
    }

    @Override
    protected TypeMap<User, UserDto> populateToDtoTypeMap(TypeMap<User, UserDto> typeMapper) {
        typeMapper.addMappings(mapper -> mapper
                .using(entitiesToIdentifiers)
                .map(
                        User::getRoles,
                        UserDto::setRoles
                )
        );
        return typeMapper;
    }

}
