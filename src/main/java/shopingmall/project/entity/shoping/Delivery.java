package shopingmall.project.entity.shoping;

import jakarta.persistence.*;
import lombok.Getter;
import shopingmall.project.type.DeliveryType;

@Entity
@Getter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.ORDINAL)
    private DeliveryType status;

    public void enableOrder(Order order) {
        this.order = order;
    }

    public Delivery(Address address, DeliveryType status) {
        this.address = address;
        this.status = status;
    }
}
