package apap.ti.silogistik2106751354.service;

import java.util.List;
import org.springframework.stereotype.Service;

import apap.ti.silogistik2106751354.model.Barang;
import apap.ti.silogistik2106751354.model.Gudang;
import apap.ti.silogistik2106751354.model.GudangBarang;

@Service
public interface GudangService {
    void addGudang(Gudang gudang);

    List<Gudang> getAllGudang();

    Gudang getGudangById(Long idGudang);

    List<GudangBarang> findGudangByBarang(Barang Barang);

    Gudang restockBarang(Barang barang, Integer jumlahBarang, Gudang gudang);

    void updateGudangBarang(Long idGudang, List<GudangBarang> listGudangBarang);

}
