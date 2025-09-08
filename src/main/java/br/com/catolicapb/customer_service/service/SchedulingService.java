package br.com.catolicapb.customer_service.service;

import br.com.catolicapb.customer_service.dto.AvailableResponseDTO;
import br.com.catolicapb.customer_service.dto.AvailableSchedulingRequestDTO;
import br.com.catolicapb.customer_service.repository.SchedulingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.catolicapb.customer_service.constants.ScheduleConstants.SHIFT_LIMIT;

@Service
@RequiredArgsConstructor
public class SchedulingService {

    private final SchedulingRepository schedulingRepository;

    private AvailableResponseDTO verifyAvailable(AvailableSchedulingRequestDTO requestDTO) {
        Long total = schedulingRepository.verifyScheduleAvailable(
                requestDTO.getDateSchedule(),
                requestDTO.getShift(),
                requestDTO.getVetId()
        );

        int availability = (int) Math.max(0, SHIFT_LIMIT - total);

        return new AvailableResponseDTO(requestDTO.getShift(), availability);
    }
}
