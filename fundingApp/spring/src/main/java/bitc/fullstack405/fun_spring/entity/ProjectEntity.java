package bitc.fullstack405.fun_spring.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project")
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "projectId")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private int projectId; // pk

    @Column(name = "goal_amount", nullable = false)
    private int goalAmount; // 목표 금액

    @Column(name = "current_amount", nullable = false)
    @ColumnDefault("0") // 기본값 0 설정, 엔티티를 save할 때 null 값은 배제하고 insert 쿼리를 날리도록 함
    private int currentAmount;  // 현재금액

    @Column(length = 50, nullable = false)
    private String title; // 제목

    @Column(length = 2000, nullable = false)
    private String contents; // 내용

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate; // 시작일

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate; // 종료일

    @Column(name = "per_price", nullable = false)
    private int perPrice; // 개당 금액

    @Column(length = 2000, nullable = false)
    private String thumbnail; // 프로젝트 썸네일

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private UserEntity user; // fk


    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<SupportEntity> supportList = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<FavoriteEntity> favoriteList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @ToString.Exclude
    private CategoryEntity category; // fk
}