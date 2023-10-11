package apap.ti.silogistik2106751354.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.ti.silogistik2106751354.model.Barang;
import apap.ti.silogistik2106751354.model.GudangBarang;
import apap.ti.silogistik2106751354.repository.GudangBarangDb;
import apap.ti.silogistik2106751354.repository.GudangDb;

@Service
public class GudangBarangServiceImpl implements GudangBarangService {

    @Autowired
    GudangBarangDb gudangBarangDb;

    @Autowired
    GudangDb gudangDb;

    @Override
    public List<GudangBarang> getAllGudangBarang() {
        return gudangBarangDb.findAll();
    }

    @Override
    public List<GudangBarang> getGudangBarangByBarang(Barang barang) {
        return gudangBarangDb.findAllBySKUBarang(barang);
    }

    @Override
    public void addGudangBarang(GudangBarang gudangBarang) {
        gudangBarangDb.save(gudangBarang);
    }

}
