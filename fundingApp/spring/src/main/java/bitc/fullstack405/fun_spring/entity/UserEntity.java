package bitc.fullstack405.fun_spring.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class UserEntity {
    @Id
    @Column(name = "user_id", nullable = false)
    private String userId; // pk

    @Column(name = "user_pw", length = 45, nullable = false)
    private String userPw; // 비밀번호

    @Column(length = 30, nullable = false)
    private String name; // 이름

    @Column(length = 45, nullable = false, unique = true)
    private String email; // 이메일

    @Column(length = 500, nullable = false)
    private String address; // 주소

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ProjectEntity> projectList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<SupportEntity> supportList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<FavoriteEntity> favoriteList = new ArrayList<>();
}