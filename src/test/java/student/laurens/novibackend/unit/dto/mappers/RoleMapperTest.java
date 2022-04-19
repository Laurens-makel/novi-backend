package student.laurens.novibackend.unit.dto.mappers;

import lombok.Getter;
import student.laurens.novibackend.entities.Role;
import student.laurens.novibackend.entities.dto.RoleDto;
import student.laurens.novibackend.entities.dto.mappers.RoleMapper;

import static org.junit.Assert.assertEquals;

public class RoleMapperTest extends ResourceMapperTest<Role, RoleDto> {
    private @Getter RoleMapper mapper = new RoleMapper();

    @Override
    protected RoleDto dto() {
        RoleDto dto = new RoleDto();
        dto.setName("My role");
        return dto;
    }

    @Override
    protected Role entity() {
        Role entity = new Role();
        entity.setId(1);
        entity.setName("Role");
        return entity;
    }

    @Override
    protected void assertToEntity(RoleDto dto, Role entity) {
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
    }

    @Override
    protected void assertToDto(Role entity, RoleDto dto) {
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
    }
}
