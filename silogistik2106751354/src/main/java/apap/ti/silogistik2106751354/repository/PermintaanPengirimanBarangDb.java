package apap.ti.silogistik2106751354.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apap.ti.silogistik2106751354.model.Barang;
import apap.ti.silogistik2106751354.model.PermintaanPengiriman;
import apap.ti.silogistik2106751354.model.PermintaanPengirimanBarang;

@Repository
public interface PermintaanPengirimanBarangDb extends JpaRepository<PermintaanPengirimanBarang, Long> {

    PermintaanPengirimanBarang findByIdPermintaanPengirimanAndSKUBarang(PermintaanPengiriman permintaanPengiriman,
            Barang skuBarang);

    List<PermintaanPengirimanBarang> findAllBySKUBarang(Barang barang);

}
