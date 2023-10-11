package apap.ti.silogistik2106751354.controller;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import apap.ti.silogistik2106751354.DTO.CreatePermintaanPengirimanRequestDTO;
import apap.ti.silogistik2106751354.DTO.PermintaanPengirimanMapper;
import apap.ti.silogistik2106751354.model.Barang;
import apap.ti.silogistik2106751354.model.PermintaanPengiriman;
import apap.ti.silogistik2106751354.model.PermintaanPengirimanBarang;
import apap.ti.silogistik2106751354.service.BarangService;
import apap.ti.silogistik2106751354.service.KaryawanService;
import apap.ti.silogistik2106751354.service.PermintaanPengirimanService;
import jakarta.validation.Valid;

@Controller
public class PermintaanPengirimanController {

    @Autowired
    private BarangService barangService;

    @Autowired
    private PermintaanPengirimanService permintaanPengirimanService;

    @Autowired
    private KaryawanService karyawanService;

    @Autowired
    private PermintaanPengirimanMapper permintaanPengirimanMapper;

    @GetMapping("/permintaan-pengiriman")
    private String viewAllPermintaanPengiriman(Model model) {
        List<PermintaanPengiriman> listPermintaanPengiriman = permintaanPengirimanService
                .getPermintaanPengirimanByStatus(false);

        model.addAttribute("listPermintaanPengiriman", listPermintaanPengiriman);
        return "view-all-permintaan-pengiriman";

    }

    @GetMapping("/filter-permintaan-pengiriman")
    private String viewFilteredPermintaanPengiriman(Model model,
            @RequestParam(name = "SKU", required = false) String SKU,
            @RequestParam(name = "start-date", required = false) LocalDate startDate,
            @RequestParam(name = "end-date", required = false) LocalDate endDate) {
        List<PermintaanPengiriman> listPermintaanPengiriman = permintaanPengirimanService
                .getPermintaanPengirimanByStatus(false);

        if (SKU != null || startDate != null || endDate != null) {
            listPermintaanPengiriman = permintaanPengirimanService.getPermintaanPengirimanByFilter(SKU, startDate,
                    endDate);
        }

        List<Barang> listBarangExisting = barangService.getAllBarang();

        if (startDate != null) {
            model.addAttribute("startDate", Date.valueOf(startDate));
        }

        if (endDate != null) {
            model.addAttribute("endDate", Date.valueOf(endDate));
        }

        model.addAttribute("listBarangExisting", listBarangExisting);
        model.addAttribute("listPermintaanPengiriman", listPermintaanPengiriman);
        model.addAttribute("selectedSKU", SKU);

        return "view-all-permintaan-pengiriman-filter";
    }

    @GetMapping("/permintaan-pengiriman/{idPermintaanPengiriman}")
    private String viewDetailPermintaanPengiriman(@PathVariable("idPermintaanPengiriman") Long idPermintaanPengiriman,
            Model model) {
        PermintaanPengiriman permintaanPengiriman = permintaanPengirimanService
                .getPermintaanPengirimanById(idPermintaanPengiriman);

        model.addAttribute("permintaanPengiriman", permintaanPengiriman);
        return "view-detail-permintaan-pengiriman";
    }

    @GetMapping("/permintaan-pengiriman/tambah")
    private String formPermintaanPengiriman(Model model) {
        CreatePermintaanPengirimanRequestDTO permintaanDTO = new CreatePermintaanPengirimanRequestDTO();

        model.addAttribute("listKaryawan", karyawanService.getAllKaryawan());
        model.addAttribute("listBarangExisting", barangService.getAllBarang());
        model.addAttribute("permintaanDTO", permintaanDTO);
        return "form-add-permintaan-pengiriman";
    }

    @PostMapping(value = "/permintaan-pengiriman/tambah", params = { "addRow" })
    public String addRow(CreatePermintaanPengirimanRequestDTO permintaanDTO, Model model) {
        if (permintaanDTO.getListBarang() == null || permintaanDTO.getListBarang().size() == 0) {
            permintaanDTO.setListBarang(new ArrayList<PermintaanPengirimanBarang>());
        }
        permintaanDTO.getListBarang().add(new PermintaanPengirimanBarang());
        model.addAttribute("listKaryawan", karyawanService.getAllKaryawan());
        model.addAttribute("listBarangExisting", barangService.getAllBarang());
        model.addAttribute("permintaanDTO", permintaanDTO);
        return "form-add-permintaan-pengiriman";
    }

    @PostMapping("/permintaan-pengiriman/tambah")
    private String addPermintaanPengiriman(@Valid CreatePermintaanPengirimanRequestDTO permintaanDTO,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("message", "Mohon periksa kembali data yang anda masukkan!");

            model.addAttribute("listKaryawan", karyawanService.getAllKaryawan());
            model.addAttribute("listBarangExisting", barangService.getAllBarang());
            model.addAttribute("permintaanDTO", permintaanDTO);

            return "form-add-permintaan-pengiriman";
        }

        for (PermintaanPengirimanBarang permintaanBarang : permintaanDTO.getListBarang()) {
            if (permintaanBarang.getKuantitas() <= 0) {
                model.addAttribute("errorMessage", "Terjadi kesalahan pada kuantitas barang!");

                model.addAttribute("listKaryawan", karyawanService.getAllKaryawan());
                model.addAttribute("listBarangExisting", barangService.getAllBarang());
                model.addAttribute("permintaanDTO", permintaanDTO);

                return "form-add-permintaan-pengiriman";
            }
        }

        if (permintaanDTO.getJenis_layanan() == 0) {
            model.addAttribute("errorMessage", "Mohon Masukkan Jenis Layanan!");

            model.addAttribute("listKaryawan", karyawanService.getAllKaryawan());
            model.addAttribute("listBarangExisting", barangService.getAllBarang());
            model.addAttribute("permintaanDTO", permintaanDTO);

            return "form-add-permintaan-pengiriman";
        }

        PermintaanPengiriman permintaanPengiriman = permintaanPengirimanMapper
                .createPermintaanPengirimanRequestDTOToPermintaanPengiriman(permintaanDTO);

        permintaanPengirimanService.addPermintaanPengiriman(permintaanPengiriman);

        permintaanDTO = new CreatePermintaanPengirimanRequestDTO();

        model.addAttribute("message",
                "Permintaan Pengiriman untuk " + permintaanPengiriman.getNama_penerima() + " berhasil ditambahkan!");
        model.addAttribute("listKaryawan", karyawanService.getAllKaryawan());
        model.addAttribute("listBarangExisting", barangService.getAllBarang());
        model.addAttribute("permintaanDTO", permintaanDTO);
        return "form-add-permintaan-pengiriman";
    }

    @GetMapping("/permintaan-pengiriman/{idPermintaanPengiriman}/cancel")
    private String cancelPermintaanPengiriman(@PathVariable("idPermintaanPengiriman") Long idPermintaanPengiriman,
            Model model) {
        PermintaanPengiriman permintaanPengiriman = permintaanPengirimanService
                .getPermintaanPengirimanById(idPermintaanPengiriman);

        LocalDateTime waktuPermintaan = permintaanPengiriman.getWaktu_permintaan();
        LocalDateTime currentTime = LocalDateTime.now();
        long hoursDifference = Duration.between(waktuPermintaan, currentTime).toHours();

        if (hoursDifference > 24) {
            model.addAttribute("errorMessage",
                    "Permintaan Pengiriman tidak dapat dibatalkan karena sudah lebih dari 24 jam sejak permintaan dibuat!");
            model.addAttribute("status", 0);

        } else {
            permintaanPengirimanService.cancelPermintaanPengiriman(permintaanPengiriman);
            model.addAttribute("successMessage", "Permintaan Pengiriman berhasil dibatalkan!");
            model.addAttribute("status", 1);

        }

        return "view-message-cancle-permintaan-pengiriman";
    }

}
