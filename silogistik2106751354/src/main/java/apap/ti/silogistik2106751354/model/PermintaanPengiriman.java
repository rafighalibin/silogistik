package apap.ti.silogistik2106751354.model;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
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
@Table(name = "permintaan_pengiriman")
public class PermintaanPengiriman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nomor_pengiriman", nullable = false)
    private String nomor_pengiriman;

    @NotNull
    @Column(name = "is_cancelled", nullable = false)
    private Boolean is_cancelled = false;

    @NotNull
    @Column(name = "nama_penerima", nullable = false)
    private String nama_penerima;

    @NotNull
    @Column(name = "alamat_penerima", nullable = false)
    private String alamat_penerima;

    @NotNull
    @Column(name = "tanggal_pengiriman", nullable = false)
    private Date tanggal_pengiriman;

    @NotNull
    @Column(name = "biaya_pengiriman", nullable = false)
    private int biaya_pengiriman;

    @NotNull
    @Column(name = "jenis_layanan", nullable = false)
    private int jenis_layanan;

    @ManyToOne
    @JoinColumn(name = "id_karyawan", referencedColumnName = "id")
    private Karyawan karyawan;

    @NotNull
    @UpdateTimestamp
    @Column(name = "waktu_permintaan", nullable = false)
    private LocalDateTime waktu_permintaan;

    @OneToMany(mappedBy = "idPermintaanPengiriman", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PermintaanPengirimanBarang> listBarang;

}
