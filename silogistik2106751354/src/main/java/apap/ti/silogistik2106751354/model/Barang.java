package apap.ti.silogistik2106751354.model;

import java.math.BigInteger;
import java.util.List;

import org.checkerframework.checker.units.qual.C;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "barang")
public class Barang {
    @Id
    @Column(name = "SKU", nullable = false)
    private String SKU;

    @NotNull
    @Column(name = "tipe_barang", nullable = false)
    private int tipe_barang;

    @NotNull
    @Column(name = "merk", nullable = false)
    private String merk;

    @NotNull
    @Size(max = 20)
    @Column(name = "harga_barang", nullable = false)
    private BigInteger harga_barang;

    @OneToMany(mappedBy = "SKUBarang", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GudangBarang> listGudangBarang;

    @OneToMany(mappedBy = "SKUBarang", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PermintaanPengirimanBarang> listPermintaanPengiriman;
}
