package apap.ti.silogistik2106751354.DTO;

import org.mapstruct.Mapper;

import apap.ti.silogistik2106751354.model.Gudang;

@Mapper(componentModel = "spring")
public interface GudangMapper {

    RestockGudangRequestDTO gudangToRestockGudangRequestDTO(Gudang gudang);

    Gudang restockGudangRequestDTOToGudang(RestockGudangRequestDTO restockGudangRequestDTO);

}