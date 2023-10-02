package apap.ti.silogistik2106751354.service;

import java.util.List;
import org.springframework.stereotype.Service;

import apap.ti.silogistik2106751354.model.Gudang;

@Service
public interface GudangService {
    void addGudang(Gudang gudang);

    List<Gudang> getAllGudang();
}
