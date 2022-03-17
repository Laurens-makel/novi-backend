package student.laurens.novibackend.users;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @Column(name = "UID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter Integer uid;

    @Column(name = "USERNAME", nullable = false, unique = true)
    private @Getter @Setter String username;

    @Column(name = "FIRSTNAME", nullable = false)
    private @Getter @Setter String firstName;

    @Column(name = "LASTNAME", nullable = false)
    private @Getter @Setter String lastName;

    @Column(name = "PASSWORD", nullable = false, length = 60)
    private @Getter String password;

    public void setPassword(String password){
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_ROLES",
            joinColumns = @JoinColumn(name = "UID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    private @Getter Set<Role> roles = new HashSet<>();

}


