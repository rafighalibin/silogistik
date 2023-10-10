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
@NoArgsConstructor
@AllArgsConstructor
public class CreateBarangRequestDTO {

    @NotNull
    private int tipe_barang;

    @NotBlank
    private String merk;

    @NotNull
    private Long harga_barang;

    private HashMap<Integer, String> opsiTipe = new HashMap<Integer, String>() {
        {
            put(1, "Produk Elektronik");
            put(2, "Pakaian & Aksesoris");
            put(3, "Makanan & Minuman");
            put(4, "Kosmetik");
            put(5, "Perlengkapan Rumah");

        }
    };
}
