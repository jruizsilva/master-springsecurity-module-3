package masterspringsecurity.domain.entity.security;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "operations")
public class OperationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    private Long id;

    private String name;
    private String path;
    private String httpMethod;
    private boolean permitAll;
    @ManyToOne
    @JoinColumn(name = "module_id")
    private ModuleEntity module;

}