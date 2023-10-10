package apap.ti.silogistik2106751354.service;

import java.util.List;

import org.springframework.stereotype.Service;

import apap.ti.silogistik2106751354.DTO.CreateBarangRequestDTO;
import apap.ti.silogistik2106751354.DTO.UpdateBarangRequestDTO;
import apap.ti.silogistik2106751354.model.Barang;

@Service
public interface BarangService {
    void addBarang(Barang barang);

    List<Barang> getAllBarang();

    Barang getBarangBySKU(String sku);

    Barang createBarang(CreateBarangRequestDTO createBarangRequestDTO);

    void updateBarang(UpdateBarangRequestDTO barang);
}
