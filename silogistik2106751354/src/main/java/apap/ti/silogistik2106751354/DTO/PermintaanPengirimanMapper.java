package apap.ti.silogistik2106751354.DTO;

import org.mapstruct.Mapper;
import apap.ti.silogistik2106751354.model.PermintaanPengiriman;

@Mapper(componentModel = "spring")
public interface PermintaanPengirimanMapper {
    PermintaanPengiriman createPermintaanPengirimanRequestDTOToPermintaanPengiriman(
            CreatePermintaanPengirimanRequestDTO createPermintaanPengirimanRequestDTO);
}
