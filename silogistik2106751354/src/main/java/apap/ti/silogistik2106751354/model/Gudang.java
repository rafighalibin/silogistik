package apap.ti.silogistik2106751354.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;

import org.checkerframework.checker.units.qual.C;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gudang")
public class Gudang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Size(max = 20)
    // TODO: Change to BigInteger
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max = 255)
    @Column(name = "alamat_gudang", nullable = false)
    private String alamat_gudang;

    @OneToMany(mappedBy = "idGudang", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GudangBarang> listBarang;
}
