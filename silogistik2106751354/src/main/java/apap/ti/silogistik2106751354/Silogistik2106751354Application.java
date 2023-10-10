package apap.ti.silogistik2106751354;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.checkerframework.checker.units.qual.g;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.github.javafaker.Faker;

import apap.ti.silogistik2106751354.model.Barang;
import apap.ti.silogistik2106751354.model.Gudang;
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
			PermintaanPengirimanService permintaanPengirimanService,
			PermintaanPengirimanBarangService permintaanPengirimanServiceBarang) {
		return args -> {
			var faker = new Faker(new Locale("in-ID"));

			// Create multiple Gudang
			List<Gudang> gudangs = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				Gudang gudang = new Gudang();
				gudang.setNama(faker.company().name());
				gudang.setAlamat_gudang(faker.address().secondaryAddress());
				gudangService.addGudang(gudang);
				gudangs.add(gudang);
			}

			// Create a list to store Barangs
			List<Barang> barangs = new ArrayList<>();

			// Create Barangs
			for (int i = 0; i < 5; i++) {
				Barang barang = new Barang();
				barang.setMerk(faker.commerce().productName());
				barang.setTipe_barang(faker.random().nextInt(1, 5));
				barang.setHarga_barang(Long.valueOf(faker.random().nextInt(300000, 1000000)));
				barang.setSKU(faker.code().ean8());
				barangService.addBarang(barang);
				barangs.add(barang);
			}

			// Randomly assign Barangs to Gudang
			for (Barang barang : barangs) {
				Collections.shuffle(gudangs); // Shuffle the list of Gudang to randomize the assignment
				int numGudangsToAssignTo = faker.random().nextInt(1, 4); // Assign each Barang to 1 to 3 Gudang

				// Ensure that numGudangsToAssignTo does not exceed the size of the gudangs list
				numGudangsToAssignTo = Math.min(numGudangsToAssignTo, gudangs.size());

				for (int i = 0; i < numGudangsToAssignTo; i++) {
					Gudang gudang = gudangs.get(i);
					gudangService.restockBarang(barang, faker.random().nextInt(1, 26), gudang);
				}
			}

			for (int i = 0; i < 5; i++) {
				PermintaanPengiriman permintaanPengiriman = new PermintaanPengiriman();

				Instant startInstant = Instant.now(); // Replace with your start time
				Instant endInstant = Instant.now().plusSeconds(3600); // Replace with your end time

				long startMillis = startInstant.toEpochMilli();
				long endMillis = endInstant.toEpochMilli();

				long randomMillisSinceEpoch = faker.number()
						.numberBetween(startMillis, endMillis);

				LocalDateTime randomDateTime = LocalDateTime.ofInstant(
						Instant.ofEpochMilli(randomMillisSinceEpoch),
						ZoneOffset.UTC);
				permintaanPengiriman.setWaktu_permintaan(randomDateTime);
				permintaanPengiriman.setNama_penerima(faker.name().fullName());
				permintaanPengiriman.setNomor_pengiriman(faker.code().ean8());
				permintaanPengiriman.setAlamat_penerima(faker.address().secondaryAddress());

				java.util.Date utilDate = new java.util.Date();
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				permintaanPengiriman.setTanggal_pengiriman(sqlDate);

				Karyawan karyawan = new Karyawan();
				karyawan.setNama(faker.name().fullName());
				karyawan.setJenis_kelamin(faker.random().nextInt(1, 2));

				java.util.Date utilDate2 = faker.date().past(1, TimeUnit.DAYS);
				java.sql.Date sqlDate2 = new java.sql.Date(utilDate2.getTime());
				karyawan.setTanggal_lahir(sqlDate2);
				karyawanService.addKaryawan(karyawan);

				// permintaanPengiriman.setKaryawan(karyawan);
				// permintaanPengiriman.setBiaya_pengiriman(faker.random().nextInt(100000,
				// 1000000));
				// permintaanPengiriman.setJenis_layanan(faker.random().nextInt(1, 5));

				// permintaanPengirimanService.addPermintaanPengiriman(permintaanPengiriman);

				// // TODO: Make it so Randomly assign N Barangs to a PermintaanPengiriman
				// int numberOfBarangsToAssign = faker.random().nextInt(1, 5); // Adjust the
				// range as needed
				// List<Barang> selectedBarangs = new ArrayList<>(barangs); // Copy the list of
				// barangs
				// Collections.shuffle(selectedBarangs); // Shuffle the list to randomize
				// selection
				// for (int j = 0; j < numberOfBarangsToAssign; j++) {
				// PermintaanPengirimanBarang permintaanPengirimanBarang = new
				// PermintaanPengirimanBarang();
				// permintaanPengirimanBarang.setKuantitas(faker.random().nextInt(1, 10));
				// permintaanPengirimanBarang.setIdPermintaanPengiriman(permintaanPengiriman);
				// permintaanPengirimanBarang.setSKUBarang(selectedBarangs.get(j)); // Assign
				// the selected Barang
				// permintaanPengirimanServiceBarang.addPermintaanPengirimanBarang(permintaanPengirimanBarang);
				// }

			}

		};
	}

}
