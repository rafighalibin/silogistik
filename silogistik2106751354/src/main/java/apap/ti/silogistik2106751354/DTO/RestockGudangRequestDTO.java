package apap.ti.silogistik2106751354.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import apap.ti.silogistik2106751354.model.GudangBarang;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestockGudangRequestDTO {

    @NotBlank
    private Long id;

    @NotBlank
    private String nama;

    @NotBlank
    private String alamat_gudang;

    private List<GudangBarang> listBarang;
}
