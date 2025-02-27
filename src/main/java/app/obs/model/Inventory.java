package app.obs.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private Integer qty;

    @Column(nullable = false)
    private Character type;
}
