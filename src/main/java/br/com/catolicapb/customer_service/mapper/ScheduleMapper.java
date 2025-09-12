package br.com.catolicapb.customer_service.mapper;

import br.com.catolicapb.customer_service.domain.Scheduling;
import br.com.catolicapb.customer_service.dto.ScheduleRequestDTO;
import br.com.catolicapb.customer_service.dto.SchedulingDetailResponseDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ScheduleMapper {

    Scheduling dtoToEntity(ScheduleRequestDTO requestDTO);

    SchedulingDetailResponseDTO entityToDetailDto(Scheduling scheduling);
}
