package apap.ti.silogistik2106751354.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import apap.ti.silogistik2106751354.service.BarangService;
import apap.ti.silogistik2106751354.service.KaryawanService;
import apap.ti.silogistik2106751354.service.GudangService;
import apap.ti.silogistik2106751354.service.PermintaanPengirimanService;

@Controller
public class HomeController {
    @Autowired
    private GudangService gudangService;

    @Autowired
    private BarangService barangService;

    @Autowired
    private PermintaanPengirimanService permintaanPengirimanService;

    @Autowired
    private KaryawanService KaryawanService;

    @GetMapping("/")
    private String home(Model model) {

        int gudangCount = gudangService.getAllGudang().size();
        int barangCount = barangService.getAllBarang().size();
        int permintaanPengirimanCount = permintaanPengirimanService.getAllPermintaanPengiriman().size();
        int karyawanCount = KaryawanService.getAllKaryawan().size();

        model.addAttribute("gudangCount", gudangCount);
        model.addAttribute("barangCount", barangCount);
        model.addAttribute("permintaanPengirimanCount", permintaanPengirimanCount);
        model.addAttribute("karyawanCount", karyawanCount);
        return "home";
    }
}
