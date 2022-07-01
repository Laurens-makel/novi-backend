package student.laurens.novibackend.entities.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserDto extends ResourceDto {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private Set<Integer> roles;
}
