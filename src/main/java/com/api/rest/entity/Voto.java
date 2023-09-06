package com.api.rest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "voto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VOTO_ID")
    private Long voto_id;

    @ManyToOne
    @JoinColumn(name = "OPCION_ID")
    private Opcion opcion;

}
