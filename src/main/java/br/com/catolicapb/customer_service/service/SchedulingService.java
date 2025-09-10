package br.com.catolicapb.customer_service.service;

import br.com.catolicapb.customer_service.dto.AvailableResponseDTO;
import br.com.catolicapb.customer_service.dto.AvailableSchedulingRequestDTO;
import br.com.catolicapb.customer_service.dto.ScheduleRequestDTO;
import br.com.catolicapb.customer_service.enums.ScheduleShift;
import br.com.catolicapb.customer_service.exceptions.LimitAvailableException;
import br.com.catolicapb.customer_service.mapper.ScheduleMapper;
import br.com.catolicapb.customer_service.repository.SchedulingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static br.com.catolicapb.customer_service.constants.ScheduleConstants.SHIFT_LIMIT;
import static br.com.catolicapb.customer_service.constants.ScheduleConstants.SHIFT_LIMIT_MESSAGE;
import static br.com.catolicapb.customer_service.enums.ScheduleStatus.OPEN;

@Service
@RequiredArgsConstructor
public class SchedulingService {

    private final SchedulingRepository schedulingRepository;
    private final ScheduleMapper scheduleMapper;

    public void save(ScheduleRequestDTO requestDTO) {
        verifyAvailableSchedule(requestDTO);

        var scheduling = scheduleMapper.dtoToEntity(requestDTO);

        scheduling.setScheduleStatus(OPEN);
        schedulingRepository.save(scheduling);
    }

    public List<AvailableResponseDTO> validateSchedulingAvailable(AvailableSchedulingRequestDTO requestDTO) {
        var listScheduleResponse = new ArrayList<AvailableResponseDTO>();

        Arrays.asList(ScheduleShift.values()).forEach(sh -> {
            requestDTO.setShift(sh);
            listScheduleResponse.add(verifyAvailable(requestDTO));
        });

        return listScheduleResponse;
    }

    public void verifyAvailableSchedule(ScheduleRequestDTO requestDTO) {
        var verifySchedule = verifyAvailable(
                new AvailableSchedulingRequestDTO(
                        requestDTO.getVetId(),
                        requestDTO.getDateSchedule(),
                        requestDTO.getScheduleShift()
                ));
        if(verifySchedule.availableNumber() == 0) {
            throw new LimitAvailableException(SHIFT_LIMIT_MESSAGE);
        }
    }

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
