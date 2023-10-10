package apap.ti.silogistik2106751354.service;

import java.util.List;

import org.springframework.stereotype.Service;

import apap.ti.silogistik2106751354.model.PermintaanPengiriman;

@Service
public interface PermintaanPengirimanService {

    List<PermintaanPengiriman> getAllPermintaanPengiriman();

    void addPermintaanPengiriman(PermintaanPengiriman permintaanPengiriman);

    PermintaanPengiriman getPermintaanPengirimanById(Long idPermintaanPengiriman);

    void cancelPermintaanPengiriman(PermintaanPengiriman permintaanPengiriman);

}
