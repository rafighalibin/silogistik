package apap.ti.silogistik2106751354.DTO;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import apap.ti.silogistik2106751354.model.Karyawan;
import apap.ti.silogistik2106751354.model.PermintaanPengirimanBarang;
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
public class CreatePermintaanPengirimanRequestDTO {

    private Boolean is_cancelled = false;

    @NotBlank
    private String nama_penerima;

    @NotBlank
    private String alamat_penerima;

    @NotNull
    private Date tanggal_pengiriman;

    @NotNull
    private int biaya_pengiriman;

    @NotNull
    private int jenis_layanan;

    @NotNull
    private Karyawan karyawan;

    @NotNull
    private List<PermintaanPengirimanBarang> listBarang;

    private HashMap<Integer, String> opsiJenisLayanan = new HashMap<Integer, String>() {
        {
            put(1, "Same Day");
            put(2, "Kilat");
            put(3, "Reguler");
            put(4, "Hemat");
        }
    };

}
