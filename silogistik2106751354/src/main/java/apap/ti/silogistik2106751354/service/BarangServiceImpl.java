package apap.ti.silogistik2106751354.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.ti.silogistik2106751354.DTO.CreateBarangRequestDTO;
import apap.ti.silogistik2106751354.DTO.UpdateBarangRequestDTO;
import apap.ti.silogistik2106751354.model.Barang;
import apap.ti.silogistik2106751354.repository.BarangDb;

@Service
public class BarangServiceImpl implements BarangService {

    @Autowired
    BarangDb barangDb;

    @Override
    public void addBarang(Barang barang) {
        barangDb.save(barang);
    }

    @Override
    public List<Barang> getAllBarang() {
        return barangDb.findAll();
    }

    @Override
    public Barang getBarangBySKU(String sku) {
        return barangDb.findBySKU(sku);
    }

    @Override
    public Barang createBarang(CreateBarangRequestDTO createBarangRequestDTO) {
        int increment = getAllBarang().size();
        String tipePrefix;
        switch (createBarangRequestDTO.getTipe_barang()) {
            case 1:
                tipePrefix = "ELEC";
                break;
            case 2:
                tipePrefix = "CLOT";
                break;
            case 3:
                tipePrefix = "FOOD";
                break;
            case 4:
                tipePrefix = "COSM";
                break;
            case 5:
                tipePrefix = "TOOL";
                break;
            default:
                throw new IllegalArgumentException("Invalid tipe_barang");
        }
        String autoIncrementStr = String.format("%03d", increment + 1);

        Barang barang = new Barang();
        barang.setSKU(tipePrefix + autoIncrementStr);
        barang.setMerk(createBarangRequestDTO.getMerk());
        barang.setTipe_barang(createBarangRequestDTO.getTipe_barang());
        barang.setHarga_barang(createBarangRequestDTO.getHarga_barang());

        return barang;

    }

    @Override
    public void updateBarang(UpdateBarangRequestDTO barangDTO) {
        Barang barang = barangDb.findBySKU(barangDTO.getSKU());
        barang.setMerk(barangDTO.getMerk());
        barang.setHarga_barang(barangDTO.getHarga_barang());
        barangDb.save(barang);
    }

}
