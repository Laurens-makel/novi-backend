package student.laurens.novibackend.dto;

import lombok.Data;
import student.laurens.novibackend.entities.Role;

import java.util.Set;

@Data
public class UserDto {
    private Integer uid;
    private String username;
    private String firstName;
    private String lastName;
    private Set<Role> roles;
}
