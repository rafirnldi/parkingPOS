package com.example.demo.dtos;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
public class CheckInDTO {


    @NotEmpty
    @NotNull
    private String plat;
    @NotEmpty
    @NotNull
    private String tipe_kendaraan;
}
