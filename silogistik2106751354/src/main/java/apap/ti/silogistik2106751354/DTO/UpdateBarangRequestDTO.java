package apap.ti.silogistik2106751354.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBarangRequestDTO extends CreateBarangRequestDTO {

    @NotBlank
    private String SKU;
}
