package apap.ti.silogistik2106751354.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.ti.silogistik2106751354.model.Barang;
import apap.ti.silogistik2106751354.model.PermintaanPengiriman;
import apap.ti.silogistik2106751354.model.PermintaanPengirimanBarang;
import apap.ti.silogistik2106751354.repository.BarangDb;
import apap.ti.silogistik2106751354.repository.PermintaanPengirimanBarangDb;
import apap.ti.silogistik2106751354.repository.PermintaanPengirimanDb;

@Service
public class PermintaanPengirimanServiceImpl implements PermintaanPengirimanService {

    @Autowired
    PermintaanPengirimanDb permintaanPengirimanDb;

    @Autowired
    PermintaanPengirimanBarangDb permintaanPengirimanBarangDb;

    @Autowired
    BarangDb barangDb;

    @Override
    public List<PermintaanPengiriman> getAllPermintaanPengiriman() {
        return permintaanPengirimanDb.findAll();
    }

    @Override
    public List<PermintaanPengiriman> getPermintaanPengirimanByStatus(boolean isCancelled) {
        List<PermintaanPengiriman> result = permintaanPengirimanDb.findByIsCancelled(isCancelled);
        result.sort(
                (PermintaanPengiriman p1, PermintaanPengiriman p2) -> p2.getWaktu_permintaan()
                        .compareTo(p1.getWaktu_permintaan()));

        return result;
    }

    @Override
    public void addPermintaanPengiriman(PermintaanPengiriman permintaanPengiriman) {

        String shipmentNumber;
        do {
            shipmentNumber = generateUniqueShipmentNumber(permintaanPengiriman);
        } while (!isShipmentNumberUnique(shipmentNumber));

        permintaanPengiriman.setNomor_pengiriman(shipmentNumber);
        permintaanPengiriman.setWaktu_permintaan(LocalDateTime.now());
        permintaanPengirimanDb.save(permintaanPengiriman);

        List<String> addedBarang = new ArrayList<String>();

        for (PermintaanPengirimanBarang permintaanPengirimanBarang : permintaanPengiriman.getListBarang()) {
            Barang barang = barangDb.findBySKU(permintaanPengirimanBarang.getSKUBarang().getSKU());
            if (addedBarang.contains(barang.getSKU())) {
                PermintaanPengirimanBarang targePermintaanPengirimanBarang = permintaanPengirimanBarangDb
                        .findByIdPermintaanPengirimanAndSKUBarang(permintaanPengiriman, barang);
                targePermintaanPengirimanBarang.setKuantitas(
                        targePermintaanPengirimanBarang.getKuantitas() + permintaanPengirimanBarang.getKuantitas());
                permintaanPengirimanBarangDb.save(targePermintaanPengirimanBarang);
            } else {
                permintaanPengirimanBarang.setSKUBarang(barang);
                permintaanPengirimanBarang.setIdPermintaanPengiriman(permintaanPengiriman);
                permintaanPengirimanBarangDb.save(permintaanPengirimanBarang);
                addedBarang.add(barang.getSKU());
            }

        }

    }

    private String generateUniqueShipmentNumber(PermintaanPengiriman permintaanPengiriman) {
        long jumlahBarang = 0;
        for (PermintaanPengirimanBarang permintaanPengirimanBarang : permintaanPengiriman.getListBarang()) {
            jumlahBarang += permintaanPengirimanBarang.getKuantitas();
        }

        String shipmentNumber = "REQ"
                + String.format("%02d", jumlahBarang % 100)
                + getJenisLayananCode(permintaanPengiriman.getJenis_layanan())
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        return shipmentNumber;
    }

    private String getJenisLayananCode(int jenisLayanan) {
        switch (jenisLayanan) {
            case 1:
                return "SAM";
            case 2:
                return "KIL";
            case 3:
                return "REG";
            case 4:
                return "HEM";
            default:
                return "UNK"; // Unknown
        }
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
        permintaanPengirimanDb.findById(permintaanPengiriman.getId()).get().setIsCancelled(true);
        permintaanPengirimanDb.save(permintaanPengiriman);
    }

    @Override
    public List<PermintaanPengiriman> getPermintaanPengirimanByFilter(String SKU, LocalDate startDate,
            LocalDate endDate) {
        List<PermintaanPengirimanBarang> permintaanBarang = new ArrayList<PermintaanPengirimanBarang>();

        permintaanBarang = permintaanPengirimanBarangDb.findAll();

        if (SKU != "") {
            Barang barang = barangDb.findBySKU(SKU);
            permintaanBarang = permintaanPengirimanBarangDb.findAllBySKUBarang(barang);
        }

        List<PermintaanPengiriman> result = new ArrayList<PermintaanPengiriman>();
        if (startDate != null || endDate != null) {
            startDate = startDate == null ? LocalDate.of(1900, 1, 1) : startDate.minusDays(1);
            endDate = endDate == null ? LocalDate.now().plusDays(1) : endDate.plusDays(1);
            for (PermintaanPengirimanBarang permintaanPengirimanBarang : permintaanBarang) {
                PermintaanPengiriman permintaanPengiriman = permintaanPengirimanBarang.getIdPermintaanPengiriman();
                if (permintaanPengiriman.getWaktu_permintaan().toLocalDate().isAfter(startDate)
                        && permintaanPengiriman.getWaktu_permintaan().toLocalDate().isBefore(endDate)) {
                    result.add(permintaanPengiriman);
                }

            }
        } else {
            for (PermintaanPengirimanBarang permintaanPengirimanBarang : permintaanBarang) {
                result.add(permintaanPengirimanBarang.getIdPermintaanPengiriman());
            }
        }

        return result;
    }

}
