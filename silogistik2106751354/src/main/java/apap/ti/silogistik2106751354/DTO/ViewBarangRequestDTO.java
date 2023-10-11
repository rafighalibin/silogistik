package apap.ti.silogistik2106751354.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewBarangRequestDTO extends UpdateBarangRequestDTO {

    private int stok = 0;
}
