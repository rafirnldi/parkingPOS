package com.example.demo.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Tickets {

    @Id
    @Column(
            name = "plat"
    )
    private String plat;

    @Column(
            name = "kendaraan"
    )
    private String kendaraan;

    @Column(
            name = "entry"
    )
    private LocalDateTime entry;

}
