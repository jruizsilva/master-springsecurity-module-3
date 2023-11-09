package masterspringsecurity.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import masterspringsecurity.domain.entity.security.ModuleEntity;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "public_operations")
public class PublicOperationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    private Long id;
    @Column(unique = true)
    private String name;
    private String path;
    private String httpMethod;
    @ManyToOne
    @JoinColumn(name = "module_id")
    private ModuleEntity module;

}