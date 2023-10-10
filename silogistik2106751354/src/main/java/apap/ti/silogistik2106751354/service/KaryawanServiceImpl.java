package apap.ti.silogistik2106751354.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.ti.silogistik2106751354.model.Gudang;
import apap.ti.silogistik2106751354.model.Karyawan;
import apap.ti.silogistik2106751354.repository.KaryawanDb;

@Service
public class KaryawanServiceImpl implements KaryawanService {
    @Autowired
    KaryawanDb karyawanDb;

    @Override
    public List<Karyawan> getAllKaryawan() {
        return karyawanDb.findAll();
    }

    @Override
    public void addKaryawan(Karyawan karyawan) {
        karyawanDb.save(karyawan);
    }
}
