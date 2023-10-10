package apap.ti.silogistik2106751354.service;

import java.util.List;

import org.springframework.stereotype.Service;

import apap.ti.silogistik2106751354.model.Barang;
import apap.ti.silogistik2106751354.model.GudangBarang;

@Service
public interface GudangBarangService {

    List<GudangBarang> getAllGudangBarang();

    List<GudangBarang> getGudangBarangByBarang(Barang barang);

    void updateGudangBarang(GudangBarang gudangBarang);

}
