package apap.ti.silogistik2106751354.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import apap.ti.silogistik2106751354.DTO.CreatePermintaanPengirimanRequestDTO;
import apap.ti.silogistik2106751354.DTO.PermintaanPengirimanMapper;
import apap.ti.silogistik2106751354.model.Barang;
import apap.ti.silogistik2106751354.model.Gudang;
import apap.ti.silogistik2106751354.model.PermintaanPengiriman;
import apap.ti.silogistik2106751354.model.PermintaanPengirimanBarang;
import apap.ti.silogistik2106751354.service.BarangService;
import apap.ti.silogistik2106751354.service.GudangService;
import apap.ti.silogistik2106751354.service.KaryawanService;
import apap.ti.silogistik2106751354.service.PermintaanPengirimanService;
import jakarta.validation.Valid;

@Controller
public class PermintaanPengirimanController {
    @Autowired
    private GudangService gudangService;

    @Autowired
    private BarangService barangService;

    @Autowired
    private PermintaanPengirimanService permintaanPengirimanService;

    @Autowired
    private KaryawanService KaryawanService;

    @Autowired
    private PermintaanPengirimanMapper permintaanPengirimanMapper;

    @GetMapping("/permintaan-pengiriman")
    private String viewAllPermintaanPengiriman(Model model) {
        List<PermintaanPengiriman> listPermintaanPengiriman = permintaanPengirimanService.getAllPermintaanPengiriman();

        // TODO: sort by latest
        // TODO: query by status == false
        model.addAttribute("listPermintaanPengiriman", listPermintaanPengiriman);
        return "view-all-permintaan-pengiriman";
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
        permintaanDTO.setListBarang(new ArrayList<PermintaanPengirimanBarang>());
        permintaanDTO.getListBarang().add(new PermintaanPengirimanBarang());

        // TODO: fix jenis layanan default value
        model.addAttribute("listKaryawan", KaryawanService.getAllKaryawan());
        model.addAttribute("listBarangExisting", barangService.getAllBarang());
        model.addAttribute("permintaanDTO", permintaanDTO);
        return "form-add-permintaan-pengiriman";
    }

    @PostMapping(value = "/permintaan-pengiriman/tambah", params = { "addRow" })
    public String addRow(CreatePermintaanPengirimanRequestDTO permintaanDTO, Model model) {
        permintaanDTO.getListBarang().add(new PermintaanPengirimanBarang());
        model.addAttribute("listKaryawan", KaryawanService.getAllKaryawan());
        model.addAttribute("listBarangExisting", barangService.getAllBarang());
        model.addAttribute("permintaanDTO", permintaanDTO);
        return "form-add-permintaan-pengiriman";
    }

    @PostMapping("/permintaan-pengiriman/tambah")
    private String addPermintaanPengiriman(@Valid CreatePermintaanPengirimanRequestDTO permintaanDTO,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("message", "Mohon periksa kembali data yang anda masukkan!");

            model.addAttribute("listKaryawan", KaryawanService.getAllKaryawan());
            model.addAttribute("listBarangExisting", barangService.getAllBarang());
            model.addAttribute("permintaanDTO", permintaanDTO);

            return "form-add-permintaan-pengiriman";
        }

        PermintaanPengiriman permintaanPengiriman = permintaanPengirimanMapper
                .createPermintaanPengirimanRequestDTOToPermintaanPengiriman(permintaanDTO);

        permintaanPengirimanService.addPermintaanPengiriman(permintaanPengiriman);

        // TODO: give a new form
        model.addAttribute("listKaryawan", KaryawanService.getAllKaryawan());
        model.addAttribute("listBarangExisting", barangService.getAllBarang());
        model.addAttribute("permintaanDTO", permintaanDTO);
        model.addAttribute("message", "Permintaan Pengiriman berhasil ditambahkan!");
        return "form-add-permintaan-pengiriman";
    }

    @GetMapping("/permintaan-pengiriman/{idPermintaanPengiriman}/cancel")
    private String cancelPermintaanPengiriman(@PathVariable("idPermintaanPengiriman") Long idPermintaanPengiriman,
            Model model) {
        PermintaanPengiriman permintaanPengiriman = permintaanPengirimanService
                .getPermintaanPengirimanById(idPermintaanPengiriman);

        permintaanPengirimanService.cancelPermintaanPengiriman(permintaanPengiriman);
        model.addAttribute("message", "Permintaan Pengiriman berhasil dibatalkan!");
        return "redirect:/permintaan-pengiriman";
    }
}
