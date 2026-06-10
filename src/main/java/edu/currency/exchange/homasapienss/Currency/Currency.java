package edu.currency.exchange.homasapienss.Currency;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "currencies")
@Entity
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 3)
    private String code;
    @Column(unique = true, nullable = false, length = 50)
    private String name;
    @Column(unique = true, nullable = false, length = 3)
    private String sign;
}
