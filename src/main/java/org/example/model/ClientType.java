package org.example.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "client_type", discriminatorType = DiscriminatorType.STRING)
public abstract class ClientType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_type_id")
    private Long id;

    public abstract int getMaxVehicle();
    public abstract double applyDiscount(double price);
    public abstract String getTypeInfo();


}
