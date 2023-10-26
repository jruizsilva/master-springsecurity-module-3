package masterspringsecurity.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import masterspringsecurity.common.util.Status;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "categories")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Status status;
}