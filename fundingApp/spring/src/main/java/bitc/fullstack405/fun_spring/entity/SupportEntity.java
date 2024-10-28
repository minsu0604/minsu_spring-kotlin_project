package bitc.fullstack405.fun_spring.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "support")
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "supportId")
public class SupportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "support_id")
    private int supportId; // pk

    @Column(nullable = false)
    private int amount; // 후원 금액


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private UserEntity user; // fk


    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @ToString.Exclude
    private ProjectEntity project; // fk
}