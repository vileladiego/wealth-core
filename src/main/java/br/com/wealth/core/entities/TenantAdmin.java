package br.com.wealth.core.entities;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@Table(name = "tb_tenant_admin")
@EntityListeners(AuditingEntityListener.class)
public class TenantAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column
    private String cnpj;

    @Column
    private String address; //TODO: verificar se é necessário filtrar por cidade ou estado ou país

    @Column
    private Boolean active = true;

    @CreatedDate
    @Column(
            nullable = false,
            updatable = false
    )
    private String createdDate;

    @LastModifiedDate
    @Column(insertable = false)
    private String lastModifiedDate;
}
