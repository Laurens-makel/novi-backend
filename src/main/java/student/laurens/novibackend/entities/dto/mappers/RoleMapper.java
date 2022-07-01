package student.laurens.novibackend.entities.dto.mappers;

import org.modelmapper.TypeMap;
import student.laurens.novibackend.entities.Role;
import student.laurens.novibackend.entities.dto.RoleDto;

public class RoleMapper extends ResourceMapper<Role, RoleDto> {
    @Override
    protected TypeMap<RoleDto, Role> populateToEntityTypeMap(TypeMap<RoleDto, Role> typeMapper) {
        return typeMapper;
    }

    @Override
    protected TypeMap<Role, RoleDto> populateToDtoTypeMap(TypeMap<Role, RoleDto> typeMapper) {
        return typeMapper;
    }
}
