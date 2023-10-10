package apap.ti.silogistik2106751354.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.ti.silogistik2106751354.model.Barang;
import apap.ti.silogistik2106751354.model.GudangBarang;
import apap.ti.silogistik2106751354.repository.GudangBarangDb;

@Service
public class GudangBarangServiceImpl implements GudangBarangService {

    @Autowired
    GudangBarangDb gudangBarangDb;

    @Override
    public List<GudangBarang> getAllGudangBarang() {
        // TODO: sort if needed
        return gudangBarangDb.findAll();
    }

    @Override
    public List<GudangBarang> getGudangBarangByBarang(Barang barang) {
        return gudangBarangDb.findAllBySKUBarang(barang);
    }

    @Override
    public void updateGudangBarang(GudangBarang gudangBarang) {
        GudangBarang targetGudangBarang = gudangBarangDb.findById(gudangBarang.getId()).get();

        if (targetGudangBarang == null) {
            gudangBarangDb.save(gudangBarang);
        } else {
            targetGudangBarang.setStok(gudangBarang.getStok());
            gudangBarangDb.save(targetGudangBarang);
        }
    }

}
