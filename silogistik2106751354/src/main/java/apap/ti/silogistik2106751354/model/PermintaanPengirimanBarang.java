package apap.ti.silogistik2106751354.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "permintaan_pengiriman_barang")
public class PermintaanPengirimanBarang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Size(max = 20)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idPermintaanPengiriman", referencedColumnName = "id")
    private PermintaanPengiriman idPermintaanPengiriman;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SKUBarang", referencedColumnName = "SKU")
    private Barang SKUBarang;

    @NotNull
    private int kuantitas;
}
