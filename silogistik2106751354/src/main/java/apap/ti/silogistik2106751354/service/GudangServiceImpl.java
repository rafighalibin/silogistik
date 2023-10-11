package apap.ti.silogistik2106751354.service;

import java.util.ArrayList;
import java.util.List;

import org.checkerframework.checker.units.qual.g;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.ti.silogistik2106751354.model.Barang;
import apap.ti.silogistik2106751354.model.Gudang;
import apap.ti.silogistik2106751354.model.GudangBarang;
import apap.ti.silogistik2106751354.repository.GudangBarangDb;
import apap.ti.silogistik2106751354.repository.GudangDb;
import jakarta.transaction.Transactional;

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

    @Override
    public void updateGudangBarang(Long idGudang, List<GudangBarang> listGudangBarang) {
        Gudang gudang = gudangDb.findById(idGudang).get();

        List<String> updatedBarang = new ArrayList<String>();

        List<GudangBarang> resultGudangBarang = new ArrayList<GudangBarang>();
        for (GudangBarang gudangBarang : listGudangBarang) {
            gudangBarang.setIdGudang(gudang);
            Barang barang = gudangBarang.getSKUBarang();
            GudangBarang targetGudangBarang = gudangBarangDb.findBySKUBarangAndIdGudang(barang, gudang);

            if (!updatedBarang.contains(barang.getSKU())) {
                if (targetGudangBarang == null) {
                    gudangBarangDb.save(gudangBarang);
                    resultGudangBarang.add(gudangBarang);
                } else {
                    targetGudangBarang.setStok(gudangBarang.getStok());
                    gudangBarangDb.save(targetGudangBarang);
                    resultGudangBarang.add(targetGudangBarang);
                }
                updatedBarang.add(barang.getSKU());
            } else {
                targetGudangBarang.setStok(targetGudangBarang.getStok() + gudangBarang.getStok());
                gudangBarangDb.save(targetGudangBarang);
                resultGudangBarang.add(targetGudangBarang);
            }

        }
        for (GudangBarang gudangBarang : gudangBarangDb.findAllByIdGudang(gudang)) {
            gudangBarangDb.delete(gudangBarang);
        }

        gudangBarangDb.saveAll(resultGudangBarang);
    }

}
