package apap.ti.silogistik2106751354.controller;

import java.util.ArrayList;
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
import apap.ti.silogistik2106751354.DTO.ViewBarangRequestDTO;
import apap.ti.silogistik2106751354.model.Barang;
import apap.ti.silogistik2106751354.model.GudangBarang;
import apap.ti.silogistik2106751354.service.BarangService;
import apap.ti.silogistik2106751354.service.GudangBarangService;
import jakarta.validation.Valid;

@Controller
public class BarangController {

    @Autowired
    private GudangBarangService gudangBarangService;

    @Autowired
    private BarangService barangService;

    @Autowired
    private BarangMapper barangMapper;

    @GetMapping("/barang")
    public String viewAllBarang(Model model) {
        List<Barang> listBarang = barangService.getAllBarang();
        List<ViewBarangRequestDTO> listBarangDTO = new ArrayList<ViewBarangRequestDTO>();

        for (Barang barang : listBarang) {
            ViewBarangRequestDTO barangDTO = barangMapper.barangToViewBarangRequestDTO(barang);
            List<GudangBarang> listGudangBarang = gudangBarangService.getGudangBarangByBarang(barang);

            int stok = 0;
            for (GudangBarang gudangBarang : listGudangBarang) {
                stok += gudangBarang.getStok();
            }
            barangDTO.setStok(stok);
            listBarangDTO.add(barangDTO);
        }
        model.addAttribute("listBarang", listBarangDTO);
        return "view-all-barang";
    }

    @GetMapping("/barang/tambah")
    public String formAddBarang(Model model) {

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
            barangDTO = new CreateBarangRequestDTO();

            model.addAttribute("barangDTO", barangDTO);
            model.addAttribute("message", "Barang " + barang.getMerk() + " berhasilditambahkan!");
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
        Barang barang = barangService.getBarangBySKU(idBarang);
        UpdateBarangRequestDTO barangDTO = barangMapper.barangToUpdateBarangRequestDTO(barang);
        model.addAttribute("barangDTO", barangDTO);
        model.addAttribute("opsiTipe", barangDTO.getOpsiTipe());

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
