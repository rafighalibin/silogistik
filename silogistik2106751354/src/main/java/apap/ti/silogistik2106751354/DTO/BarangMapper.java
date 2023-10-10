package apap.ti.silogistik2106751354.DTO;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.mapstruct.AfterMapping;

import apap.ti.silogistik2106751354.model.Barang;
import apap.ti.silogistik2106751354.service.BarangService;
import jakarta.validation.Valid;

@Mapper(componentModel = "spring")
public interface BarangMapper {

    CreateBarangRequestDTO barangToCreateBarangRequestDTO(Barang barang);

    Barang createBarangRequestDTOToBarang(CreateBarangRequestDTO createBarangRequestDTO);

    UpdateBarangRequestDTO barangToUpdateBarangRequestDTO(Barang barang);

}
