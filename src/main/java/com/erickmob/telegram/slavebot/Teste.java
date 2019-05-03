package com.erickmob.telegram.slavebot;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Teste")
@Data
public class Teste {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
}
