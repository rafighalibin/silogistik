package apap.ti.silogistik2106751354;

import java.sql.Date;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.github.javafaker.Faker;

import apap.ti.silogistik2106751354.DTO.CreateBarangRequestDTO;
import apap.ti.silogistik2106751354.DTO.CreatePermintaanPengirimanRequestDTO;
import apap.ti.silogistik2106751354.DTO.PermintaanPengirimanMapper;
import apap.ti.silogistik2106751354.model.Barang;
import apap.ti.silogistik2106751354.model.Gudang;
import apap.ti.silogistik2106751354.model.GudangBarang;
import apap.ti.silogistik2106751354.model.Karyawan;
import apap.ti.silogistik2106751354.model.PermintaanPengiriman;
import apap.ti.silogistik2106751354.model.PermintaanPengirimanBarang;
import apap.ti.silogistik2106751354.service.BarangService;
import apap.ti.silogistik2106751354.service.GudangBarangService;
import apap.ti.silogistik2106751354.service.GudangService;
import apap.ti.silogistik2106751354.service.KaryawanService;
import apap.ti.silogistik2106751354.service.PermintaanPengirimanBarangService;
import apap.ti.silogistik2106751354.service.PermintaanPengirimanService;
import jakarta.transaction.Transactional;

@SpringBootApplication
public class Silogistik2106751354Application {

	public static void main(String[] args) {
		SpringApplication.run(Silogistik2106751354Application.class, args);

	}

	@Bean
	@Transactional
	CommandLineRunner run(GudangService gudangService, BarangService barangService, KaryawanService karyawanService,
			PermintaanPengirimanService permintaanPengirimanService, GudangBarangService gudangBarangService,
			PermintaanPengirimanBarangService permintaanPengirimanServiceBarang,
			PermintaanPengirimanMapper permintaanPengirimanMapper) {
		return args -> {
			var faker = new Faker(new Locale("ID"));

			int dataAmount = 1;
			// Generate dummy data for Gudang
			for (int i = 0; i < dataAmount; i++) {
				Gudang gudang = new Gudang();
				gudang.setNama(faker.address().city());
				gudang.setAlamat_gudang(faker.address().fullAddress());

				gudangService.addGudang(gudang);
			}

			// Generate dummy data for Karyawan
			for (int i = 0; i < dataAmount; i++) {
				Karyawan karyawan = new Karyawan();
				karyawan.setNama(faker.name().fullName());
				karyawan.setJenis_kelamin(faker.number().numberBetween(1, 2)); // 1 for male, 2 for female
				java.util.Date utilDate = faker.date().birthday();
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				karyawan.setTanggal_lahir(sqlDate);

				karyawanService.addKaryawan(karyawan);
			}

			// Generate dummy data for Barang
			for (int i = 0; i < dataAmount; i++) {
				CreateBarangRequestDTO createBarangRequestDTO = new CreateBarangRequestDTO();
				createBarangRequestDTO.setMerk(faker.commerce().productName());
				createBarangRequestDTO.setTipe_barang(faker.number().numberBetween(1, 5));
				createBarangRequestDTO.setHarga_barang((long) faker.number().numberBetween(100000, 1000000));
				Barang barang = barangService.createBarang(createBarangRequestDTO);

				barangService.addBarang(barang);
			}

			// Generate dummy data for Gudang Barang
			for (int i = 0; i < dataAmount; i++) {
				List<Barang> listBarang = barangService.getAllBarang();
				List<Gudang> listGudang = gudangService.getAllGudang();

				GudangBarang gudangBarang = new GudangBarang();
				gudangBarang.setSKUBarang(listBarang.get(faker.number().numberBetween(0, listBarang.size() - 1)));
				gudangBarang.setIdGudang(listGudang.get(faker.number().numberBetween(0, listGudang.size() - 1)));
				gudangBarang.setStok(faker.number().numberBetween(1, 100));
				gudangService.restockBarang(gudangBarang.getSKUBarang(), gudangBarang.getStok(),
						gudangBarang.getIdGudang());
			}

			// Generate dummy data for Permintaan Pengiriman
			for (int i = 0; i < dataAmount; i++) {
				List<Karyawan> listKaryawan = karyawanService.getAllKaryawan();
				List<Barang> listBarang = barangService.getAllBarang();

				CreatePermintaanPengirimanRequestDTO createPermintaanPengirimanRequestDTO = new CreatePermintaanPengirimanRequestDTO();
				createPermintaanPengirimanRequestDTO.setNama_penerima(faker.name().fullName());
				createPermintaanPengirimanRequestDTO.setAlamat_penerima(faker.address().fullAddress());
				createPermintaanPengirimanRequestDTO.setTanggal_pengiriman(Date.valueOf(
						faker.date().future(1, TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC).toLocalDate()));
				createPermintaanPengirimanRequestDTO.setBiaya_pengiriman(faker.number().numberBetween(100000, 1000000));
				createPermintaanPengirimanRequestDTO.setJenis_layanan(faker.number().numberBetween(1, 4));
				createPermintaanPengirimanRequestDTO
						.setKaryawan(listKaryawan.get(faker.number().numberBetween(0, listKaryawan.size() - 1)));
				createPermintaanPengirimanRequestDTO.setListBarang(new ArrayList<PermintaanPengirimanBarang>());

				for (int j = 0; j < faker.number().numberBetween(1, listBarang.size()); j++) {
					PermintaanPengirimanBarang permintaanPengirimanBarang = new PermintaanPengirimanBarang();
					permintaanPengirimanBarang
							.setSKUBarang(listBarang.get(faker.number().numberBetween(0, listBarang.size() - 1)));
					permintaanPengirimanBarang.setKuantitas(faker.number().numberBetween(1, 100));
					createPermintaanPengirimanRequestDTO.getListBarang().add(permintaanPengirimanBarang);
				}

				PermintaanPengiriman permintaanPengiriman = permintaanPengirimanMapper
						.createPermintaanPengirimanRequestDTOToPermintaanPengiriman(
								createPermintaanPengirimanRequestDTO);
				permintaanPengirimanService.addPermintaanPengiriman(permintaanPengiriman);
			}

		};
	}

}
