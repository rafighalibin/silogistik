package apap.ti.silogistik2106751354.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.ti.silogistik2106751354.model.PermintaanPengiriman;
import apap.ti.silogistik2106751354.model.PermintaanPengirimanBarang;
import apap.ti.silogistik2106751354.repository.PermintaanPengirimanDb;

@Service
public class PermintaanPengirimanServiceImpl implements PermintaanPengirimanService {

    @Autowired
    PermintaanPengirimanDb permintaanPengirimanDb;

    @Override
    public List<PermintaanPengiriman> getAllPermintaanPengiriman() {
        return permintaanPengirimanDb.findAll();
    }

    @Override
    public void addPermintaanPengiriman(PermintaanPengiriman permintaanPengiriman) {

        // Generate a unique shipment number
        String shipmentNumber;
        do {
            shipmentNumber = generateUniqueShipmentNumber(permintaanPengiriman);
        } while (!isShipmentNumberUnique(shipmentNumber));

        // Set the generated shipment number and other properties
        permintaanPengiriman.setNomor_pengiriman(shipmentNumber);
        permintaanPengiriman.setWaktu_permintaan(LocalDateTime.now());
        permintaanPengirimanDb.save(permintaanPengiriman);

        for (PermintaanPengirimanBarang permintaanPengirimanBarang : permintaanPengiriman.getListBarang()) {
            permintaanPengirimanBarang.setIdPermintaanPengiriman(permintaanPengiriman);
            permintaanPengirimanDb.save(permintaanPengiriman);
        }
    }

    private String generateUniqueShipmentNumber(PermintaanPengiriman permintaanPengiriman) {
        // Calculate the shipment number based on the specified format
        // You can use permintaanPengiriman to get the required information
        // Example: REQ05SAM15:57:22
        // You may need to adjust this part based on your specific requirements.
        // For demonstration purposes, I'm using a simple format.
        String shipmentNumber = "REQ"
                + String.format("%02d", permintaanPengiriman.getListBarang().size() % 100)
                + getJenisLayananCode(permintaanPengiriman.getJenis_layanan())
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        return shipmentNumber;
    }

    private String getJenisLayananCode(int jenisLayanan) {
        // Map jenis layanan codes to the specified values (1 = SAM, 2 = KIL, etc.)
        // You may need to adjust this mapping based on your specific values.
        Map<Integer, String> jenisLayananMap = new HashMap<>();
        jenisLayananMap.put(1, "SAM");
        jenisLayananMap.put(2, "KIL");
        jenisLayananMap.put(3, "REG");
        jenisLayananMap.put(4, "HEM");

        return jenisLayananMap.getOrDefault(jenisLayanan, "UNK"); // Default to "UNK" for unknown values
    }

    private boolean isShipmentNumberUnique(String shipmentNumber) {
        return permintaanPengirimanDb.findByNomor_pengiriman(shipmentNumber) == null;
    }

    @Override
    public PermintaanPengiriman getPermintaanPengirimanById(Long idPermintaanPengiriman) {
        return permintaanPengirimanDb.findById(idPermintaanPengiriman).get();
    }

    @Override
    public void cancelPermintaanPengiriman(PermintaanPengiriman permintaanPengiriman) {
        permintaanPengirimanDb.findById(permintaanPengiriman.getId()).get().setIs_cancelled(true);
        permintaanPengirimanDb.save(permintaanPengiriman);
    }
}
