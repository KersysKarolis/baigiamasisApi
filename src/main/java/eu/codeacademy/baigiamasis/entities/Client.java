package eu.codeacademy.baigiamasis.entities;

import eu.codeacademy.baigiamasis.enumerators.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column
    private String password;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private Integer phoneNumber;
    @Column
    private String email;
    @Column
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Order> orderList;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;


    @PrePersist
    void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
