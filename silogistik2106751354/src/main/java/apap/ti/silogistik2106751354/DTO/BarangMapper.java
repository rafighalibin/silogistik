package apap.ti.silogistik2106751354.DTO;

import org.mapstruct.Mapper;

import apap.ti.silogistik2106751354.model.Barang;

@Mapper(componentModel = "spring")
public interface BarangMapper {

    CreateBarangRequestDTO barangToCreateBarangRequestDTO(Barang barang);

    Barang createBarangRequestDTOToBarang(CreateBarangRequestDTO createBarangRequestDTO);

    UpdateBarangRequestDTO barangToUpdateBarangRequestDTO(Barang barang);

    ViewBarangRequestDTO barangToViewBarangRequestDTO(Barang barang);
}
