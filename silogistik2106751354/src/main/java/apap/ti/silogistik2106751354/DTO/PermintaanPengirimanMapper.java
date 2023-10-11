package apap.ti.silogistik2106751354.DTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import apap.ti.silogistik2106751354.model.PermintaanPengiriman;

@Mapper(componentModel = "spring")
public interface PermintaanPengirimanMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nomor_pengiriman", ignore = true)
    @Mapping(target = "waktu_permintaan", ignore = true)
    PermintaanPengiriman createPermintaanPengirimanRequestDTOToPermintaanPengiriman(
            CreatePermintaanPengirimanRequestDTO createPermintaanPengirimanRequestDTO);
}
