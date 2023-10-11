package apap.ti.silogistik2106751354.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.ti.silogistik2106751354.model.PermintaanPengirimanBarang;
import apap.ti.silogistik2106751354.repository.PermintaanPengirimanBarangDb;
import apap.ti.silogistik2106751354.repository.PermintaanPengirimanDb;

@Service
public class PermintaanPengirimanBarangServiceImpl implements PermintaanPengirimanBarangService {

    @Autowired
    PermintaanPengirimanDb permintaanPengirimanDb;

    @Autowired
    PermintaanPengirimanBarangDb permintaanPengirimanBarangDb;

    @Override
    public void addPermintaanPengirimanBarang(PermintaanPengirimanBarang permintaanPengirimanBarang) {
        permintaanPengirimanBarangDb.save(permintaanPengirimanBarang);
    }

}
