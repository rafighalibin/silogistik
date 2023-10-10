package apap.ti.silogistik2106751354.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import apap.ti.silogistik2106751354.DTO.BarangMapper;
import apap.ti.silogistik2106751354.DTO.CreateBarangRequestDTO;
import apap.ti.silogistik2106751354.DTO.UpdateBarangRequestDTO;
import apap.ti.silogistik2106751354.model.Barang;
import apap.ti.silogistik2106751354.model.Gudang;
import apap.ti.silogistik2106751354.model.GudangBarang;
import apap.ti.silogistik2106751354.service.BarangService;
import apap.ti.silogistik2106751354.service.GudangBarangService;
import apap.ti.silogistik2106751354.service.GudangService;
import jakarta.validation.Valid;

@Controller
public class BarangController {
    @Autowired
    private GudangService gudangService;

    @Autowired
    private GudangBarangService gudangBarangService;

    @Autowired
    private BarangService barangService;

    @Autowired
    private BarangMapper barangMapper;

    @GetMapping("/barang/tambah")
    public String formAddBarang(Model model) {
        String message;

        CreateBarangRequestDTO barangDTO = new CreateBarangRequestDTO();

        model.addAttribute("barangDTO", barangDTO);
        return "form-add-barang";
    }

    @PostMapping("/barang/tambah")
    public String tambahBarang(
            @Valid @ModelAttribute("barangDTO") CreateBarangRequestDTO barangDTO,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("message", "Mohon periksa kembali data yang anda masukkan!");
            model.addAttribute("barangDTO", barangDTO);

            return "form-add-barang";
        } else {
            Barang barang = barangService.createBarang(barangDTO);
            barangService.addBarang(barang);
            // TODO: change the DTO when success
            model.addAttribute("barangDTO", barangDTO);
            model.addAttribute("message", "Barang berhasil ditambahkan!");
            return "form-add-barang";

        }
    }

    @GetMapping("/barang/{idBarang}")
    public String detailBarang(
            @ModelAttribute("idBarang") String idBarang,
            Model model) {
        Barang barang = barangService.getBarangBySKU(idBarang);
        List<GudangBarang> listGudang = gudangBarangService.getGudangBarangByBarang(barang);
        model.addAttribute("barang", barang);
        model.addAttribute("listGudangBarang", listGudang);
        return "view-detail-barang";
    }

    @GetMapping("/barang/{idBarang}/ubah")
    public String formUpdateBarang(
            @ModelAttribute("idBarang") String idBarang,
            Model model) {
        // TODO: insert text tipe barang
        Barang barang = barangService.getBarangBySKU(idBarang);
        UpdateBarangRequestDTO barangDTO = barangMapper.barangToUpdateBarangRequestDTO(barang);
        model.addAttribute("barangDTO", barangDTO);
        return "form-update-barang";
    }

    @PostMapping("/barang/{idBarang}/ubah")
    public String updateBarang(
            @Valid @ModelAttribute("barangDTO") UpdateBarangRequestDTO barangDTO,
            @ModelAttribute("idBarang") String idBarang,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("message", "Mohon periksa kembali data yang anda masukkan!");
            model.addAttribute("barangDTO", barangDTO);

            return "form-update-barang";
        } else {
            barangService.updateBarang(barangDTO);

            model.addAttribute("barangDTO", barangDTO);
            model.addAttribute("message", "Barang berhasil diubah!");
            return "form-update-barang";

        }
    }
}
