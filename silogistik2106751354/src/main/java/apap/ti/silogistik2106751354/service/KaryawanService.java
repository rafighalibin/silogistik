package apap.ti.silogistik2106751354.service;

import java.util.List;

import org.springframework.stereotype.Service;

import apap.ti.silogistik2106751354.model.Karyawan;

@Service
public interface KaryawanService {

    List<Karyawan> getAllKaryawan();

    void addKaryawan(Karyawan karyawan);

}
