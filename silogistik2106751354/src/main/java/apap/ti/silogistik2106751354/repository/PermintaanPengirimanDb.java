package apap.ti.silogistik2106751354.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import apap.ti.silogistik2106751354.model.PermintaanPengiriman;

@Repository
public interface PermintaanPengirimanDb extends JpaRepository<PermintaanPengiriman, Long> {

    @Query("SELECT p FROM PermintaanPengiriman p WHERE p.nomor_pengiriman = :nomorValue")
    PermintaanPengiriman findByNomor_pengiriman(@Param("nomorValue") String nomorValue);
}
