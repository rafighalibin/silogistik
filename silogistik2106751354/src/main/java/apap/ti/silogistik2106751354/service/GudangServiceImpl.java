package apap.ti.silogistik2106751354.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.ti.silogistik2106751354.model.Gudang;
import apap.ti.silogistik2106751354.repository.GudangDb;

@Service
public class GudangServiceImpl implements GudangService {

    @Autowired
    GudangDb gudangDb;

    @Override
    public void addGudang(Gudang gudang) {
        gudangDb.save(gudang);
    }

    @Override
    public List<Gudang> getAllGudang() {
        return gudangDb.findAll();
    }
}
