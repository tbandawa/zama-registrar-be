package me.tbandawa.web.skyzmetro.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "provinces")
public class Province {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String name;

    @Column(length = 10)
    private String code;
}
