package apap.ti.silogistik2106751354.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import apap.ti.silogistik2106751354.model.Gudang;
import apap.ti.silogistik2106751354.service.GudangService;

@Controller
public class GudangController {

    @Autowired
    private GudangService gudangService;

    @GetMapping("/")
    private String home() {

        Gudang gudang = new Gudang();
        gudang.setNama("Gudang");
        gudang.setAlamat_gudang("Alamat Gudang");
        gudangService.addGudang(gudang);

        return "home";
    }
}
