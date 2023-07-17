package eu.codeacademy.baigiamasis.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Integer orderNumber;
    @ManyToOne
    private Employee employee;
    @ManyToOne
    private User user;
   @Column
    private LocalDateTime orderCreatedAt;
   @Column
    private LocalDateTime orderUpdatedAt;
   @PrePersist
    void prePersist(){
       this.orderCreatedAt = LocalDateTime.now();
   }
   @PreUpdate
    void preUpdate(){
       this.orderUpdatedAt = LocalDateTime.now();
   }
}
