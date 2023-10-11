package apap.ti.silogistik2106751354.service;

import java.util.List;

import org.springframework.stereotype.Service;

import apap.ti.silogistik2106751354.model.Barang;
import apap.ti.silogistik2106751354.model.PermintaanPengiriman;
import apap.ti.silogistik2106751354.model.PermintaanPengirimanBarang;

@Service
public interface PermintaanPengirimanBarangService {

    void addPermintaanPengirimanBarang(PermintaanPengirimanBarang permintaanPengirimanBarang);

}
