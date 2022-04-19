package student.laurens.novibackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import javax.persistence.*;

/**
 * Entity class for USERS table.
 *
 * Joins ROLES table using USER_ROLES table.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractOwnedEntity {

    @Id
    @Column(name = "UID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Integer id;

    @Column(name = "USERNAME", nullable = false, unique = true)
    private @Getter @Setter String username;

    @Column(name = "FIRSTNAME", nullable = false)
    private @Getter @Setter String firstName;

    @Column(name = "LASTNAME", nullable = false)
    private @Getter @Setter String lastName;

    @Column(name = "PASSWORD", nullable = false, length = 60)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private @Getter String password;

    public void setPassword(String password){
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_ROLES",
            joinColumns = @JoinColumn(name = "UID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    private @Getter @Setter Set<Role> roles = new HashSet<>();

    public boolean hasRole(String name){
        return getRoles()
                .stream()
                .peek((role -> log.info("User has role ["+role.getName()+"]")))
                .anyMatch( (role -> role.getName().equalsIgnoreCase(name)));
    }
    public boolean hasAuthority(String name){
        return getRoles()
                .stream()
                .peek((role -> log.info("User has role ["+role.getName()+"]")))
                .anyMatch( (role ->
                        role.getAuthorities()
                                .stream()
                                .anyMatch(authority -> authority.getName().equals(name))));
    }

    @Override
    public Integer getOwnerUid() {
        return getId();
    }
}


