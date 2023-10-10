package apap.ti.silogistik2106751354.DTO;

import java.util.HashMap;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class UpdateBarangRequestDTO extends CreateBarangRequestDTO {

    @NotBlank
    // TODO: Might be null
    private String SKU;
}
