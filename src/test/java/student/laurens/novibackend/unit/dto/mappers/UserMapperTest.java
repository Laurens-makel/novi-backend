package student.laurens.novibackend.unit.dto.mappers;

import lombok.Getter;
import student.laurens.novibackend.entities.Role;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.entities.dto.UserDto;
import student.laurens.novibackend.entities.dto.mappers.UserMapper;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class UserMapperTest extends ResourceMapperTest<User, UserDto> {
    private @Getter UserMapper mapper = new UserMapper();

    @Override
    protected UserDto dto() {
        UserDto dto = new UserDto();
        dto.setUsername("Hillbilly123");
        dto.setFirstName("Billy");
        dto.setLastName("Sweat");

        Set<Integer> roles = new HashSet<>();
        roles.add(1);
        roles.add(2);
        roles.add(3);
        dto.setRoles(roles);

        return dto;
    }

    @Override
    protected User entity() {
        User entity = new User();
        entity.setUsername("Hillbilly123");
        entity.setFirstName("Billy");
        entity.setLastName("Sweat");

        Role role = new Role();
        role.setId(1);
        role.setName("Test role 1");
        role.setId(2);
        role.setName("Test role 2");
        role.setId(3);
        role.setName("Test role 3");

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        entity.setRoles(roles);

        return entity;
    }

    @Override
    protected void assertToEntity(UserDto dto, User entity) {
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getUsername(), dto.getUsername());
        assertEquals(entity.getFirstName(), dto.getFirstName());
        assertEquals(entity.getLastName(), dto.getLastName());
        assertDtoSetMatchesEntitySet(dto.getRoles(), entity.getRoles());
    }

    @Override
    protected void assertToDto(User entity, UserDto dto) {
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getUsername(), entity.getUsername());
        assertEquals(dto.getFirstName(), entity.getFirstName());
        assertEquals(dto.getLastName(), entity.getLastName());
        assertEntitySetMatchesDtoSet(entity.getRoles(), dto.getRoles());
    }
}
