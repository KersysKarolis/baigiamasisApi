package eu.codeacademy.baigiamasis.entities;

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
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String email;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "employee")
    private List<Order> orderList;

    @PrePersist
    void prePersist(){
        this.createdAt = LocalDateTime.now();
    }
    @PreUpdate
    void preUpdate (){
        this.updatedAt = LocalDateTime.now();
    }
}
