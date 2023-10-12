package apap.ti.silogistik2106751354.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import apap.ti.silogistik2106751354.DTO.GudangMapper;
import apap.ti.silogistik2106751354.DTO.RestockGudangRequestDTO;
import apap.ti.silogistik2106751354.model.Barang;
import apap.ti.silogistik2106751354.model.Gudang;
import apap.ti.silogistik2106751354.model.GudangBarang;
import apap.ti.silogistik2106751354.service.BarangService;
import apap.ti.silogistik2106751354.service.GudangBarangService;
import apap.ti.silogistik2106751354.service.GudangService;

@Controller
public class GudangController {

    @Autowired
    private GudangService gudangService;

    @Autowired
    private GudangBarangService gudangBarangService;

    @Autowired
    private BarangService barangService;

    @Autowired
    private GudangMapper gudangMapper;

    @GetMapping("/gudang")
    public String viewAllGudang(Model model) {
        List<Gudang> listGudang = gudangService.getAllGudang();

        model.addAttribute("listGudang", listGudang);
        return "view-all-gudang";
    }

    @GetMapping("/gudang/{idGudang}")
    public String viewDetailGudang(@PathVariable("idGudang") Long idGudang, Model model) {
        Gudang gudang = gudangService.getGudangById(idGudang);
        List<GudangBarang> listGudangBarang = gudang.getListBarang();

        model.addAttribute("gudang", gudang);
        model.addAttribute("listgudangbarang", listGudangBarang);
        return "view-detail-gudang";
    }

    @GetMapping("/gudang/cari-barang")
    public String viewCariBarang(Model model, @RequestParam(name = "SKU", required = false) String sku) {
        List<Barang> listBarang = barangService.getAllBarang();
        model.addAttribute("listBarang", listBarang);

        if (sku == null) {
            return "view-cari-barang";
        }

        Barang barang = barangService.getBarangBySKU(sku);
        List<GudangBarang> listGudangBarang = gudangBarangService.getGudangBarangByBarang(barang);

        model.addAttribute("listGudangBarang", listGudangBarang);
        model.addAttribute("SKU", sku);

        return "view-cari-barang";
    }

    @GetMapping("/gudang/{idGudang}/restock-barang")
    public String viewRestockBarang(
            @PathVariable("idGudang") Long idGudang, Model model) {
        Gudang gudang = gudangService.getGudangById(idGudang);
        List<Barang> listBarang = barangService.getAllBarang();

        RestockGudangRequestDTO restockDTO = gudangMapper.gudangToRestockGudangRequestDTO(gudang);
        model.addAttribute("listBarangExisting", listBarang);
        model.addAttribute("restockDTO", restockDTO);
        model.addAttribute("id", idGudang);

        return "form-restock-barang";
    }

    @PostMapping(value = "/gudang/{idGudang}/restock-barang", params = { "addRow" })
    public String addRow(@PathVariable("idGudang") Long idGudang, @ModelAttribute RestockGudangRequestDTO restockDTO,
            Model model) {

        if (restockDTO.getListBarang() == null || restockDTO.getListBarang().size() == 0) {
            restockDTO.setListBarang(new ArrayList<GudangBarang>());
        }

        List<Barang> listBarang = barangService.getAllBarang();

        restockDTO.getListBarang().add(new GudangBarang());
        model.addAttribute("listBarangExisting", listBarang);
        model.addAttribute("restockDTO", restockDTO);
        model.addAttribute("id", idGudang);

        return "form-restock-barang";
    }

    @PostMapping(value = "/gudang/{idGudang}/restock-barang")
    public String restockBarang(@PathVariable("idGudang") Long idGudang,
            @ModelAttribute RestockGudangRequestDTO restockDTO, Model model) {

        List<GudangBarang> listGudangBarang = restockDTO.getListBarang();

        gudangService.updateGudangBarang(idGudang, listGudangBarang);

        List<Barang> listBarang = barangService.getAllBarang();
        restockDTO = gudangMapper
                .gudangToRestockGudangRequestDTO(gudangService.getGudangById(idGudang));

        model.addAttribute("listBarangExisting", listBarang);
        model.addAttribute("restockDTO", restockDTO);
        model.addAttribute("id", idGudang);
        model.addAttribute("message", "Berhasil restock barang!");

        return "form-restock-barang";
    }

}
