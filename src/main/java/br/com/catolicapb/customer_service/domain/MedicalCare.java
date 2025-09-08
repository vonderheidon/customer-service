package br.com.catolicapb.customer_service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalCare extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timeCare;

    @OneToOne
    @JoinColumn(name = "schedule_id")
    private Scheduling scheduling;

    private BigDecimal total;

    private BigDecimal procedureValue;

    @OneToMany(mappedBy = "medicalCare", cascade = CascadeType.ALL)
    private List<ItemProduct> products;
}
