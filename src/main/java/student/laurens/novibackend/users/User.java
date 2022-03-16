package student.laurens.novibackend.users;

import lombok.Getter;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @Column(name = "UID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter Long uid;

    @Column(name = "USERNAME")
    private @Getter String username;

    @Column(name = "FIRSTNAME")
    private @Getter String firstName;

    @Column(name = "LASTNAME")
    private @Getter String lastName;

    @Column(name = "PASSWORD")
    private @Getter String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_ROLES",
            joinColumns = @JoinColumn(name = "UID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    private @Getter Set<Role> roles = new HashSet<>();

}


