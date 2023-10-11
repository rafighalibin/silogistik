package apap.ti.silogistik2106751354.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apap.ti.silogistik2106751354.model.Barang;
import apap.ti.silogistik2106751354.model.Gudang;
import apap.ti.silogistik2106751354.model.GudangBarang;

@Repository
public interface GudangBarangDb extends JpaRepository<GudangBarang, Long> {
    List<GudangBarang> findAllBySKUBarang(Barang barang);

    GudangBarang findBySKUBarangAndIdGudang(Barang barang, Gudang gudang);

    void deleteAllByIdGudang(Gudang gudang);

    void deleteAllById(Long valueOf);

    List<GudangBarang> findAllByIdGudang(Gudang gudang);

}
