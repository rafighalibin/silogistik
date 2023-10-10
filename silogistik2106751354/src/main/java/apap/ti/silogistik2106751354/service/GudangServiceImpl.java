package apap.ti.silogistik2106751354.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.ti.silogistik2106751354.model.Barang;
import apap.ti.silogistik2106751354.model.Gudang;
import apap.ti.silogistik2106751354.model.GudangBarang;
import apap.ti.silogistik2106751354.repository.GudangBarangDb;
import apap.ti.silogistik2106751354.repository.GudangDb;

@Service
public class GudangServiceImpl implements GudangService {

    @Autowired
    GudangDb gudangDb;

    @Autowired
    GudangBarangDb gudangBarangDb;

    @Override
    public void addGudang(Gudang gudang) {
        gudangDb.save(gudang);
    }

    @Override
    public List<Gudang> getAllGudang() {
        return gudangDb.findAll();
    }

    @Override
    public List<GudangBarang> findGudangByBarang(Barang barang) {
        List<GudangBarang> listGudangBarang = gudangBarangDb.findAllBySKUBarang(barang);
        return listGudangBarang;
    }

    @Override
    public Gudang restockBarang(Barang barang, Integer jumlahBarang, Gudang gudang) {
        GudangBarang gudangBarang = gudangBarangDb.findBySKUBarangAndIdGudang(barang, gudang);
        if (gudangBarang == null) {
            gudangBarang = new GudangBarang();
            gudangBarang.setSKUBarang(barang);
            gudangBarang.setIdGudang(gudang);
        }

        gudangBarang.setStok(gudangBarang.getStok() + jumlahBarang);
        gudangBarangDb.save(gudangBarang);
        return gudang;

    }

    @Override
    public Gudang getGudangById(Long idGudang) {
        return gudangDb.findById(idGudang).get();
    }

}
