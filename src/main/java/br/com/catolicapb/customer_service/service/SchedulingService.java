package br.com.catolicapb.customer_service.service;

import br.com.catolicapb.customer_service.domain.Scheduling;
import br.com.catolicapb.customer_service.dto.*;
import br.com.catolicapb.customer_service.enums.ScheduleShift;
import br.com.catolicapb.customer_service.enums.ScheduleStatus;
import br.com.catolicapb.customer_service.exceptions.LimitAvailableException;
import br.com.catolicapb.customer_service.exceptions.SchedulingNotFoundException;
import br.com.catolicapb.customer_service.mapper.ScheduleMapper;
import br.com.catolicapb.customer_service.repository.SchedulingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public SchedulingDetailResponseDTO update(Long id, SchedeUpdateRequestDTO requestDTO) {
        Scheduling scheduling = schedulingRepository.findById(id)
                .orElseThrow(() -> new SchedulingNotFoundException("Agendamento não encontrado com o ID: " + id));

        boolean hasDateChanged = !scheduling.getDateSchedule().equals(requestDTO.getDateSchedule());
        boolean hasShiftChanged = !scheduling.getScheduleShift().equals(requestDTO.getScheduleShift());

        if (hasDateChanged || hasShiftChanged) {
            var validationDto = ScheduleRequestDTO.builder()
                    .vetId(scheduling.getVetId())
                    .dateSchedule(requestDTO.getDateSchedule())
                    .scheduleShift(requestDTO.getScheduleShift())
                    .build();
            verifyAvailableSchedule(validationDto);
        }

        scheduling.setVetId(requestDTO.getVetId());
        scheduling.setDateSchedule(requestDTO.getDateSchedule());
        scheduling.setScheduleShift(requestDTO.getScheduleShift());
        scheduling.setScheduleStatus(requestDTO.getScheduleStatus());

        Scheduling updatedScheduling = schedulingRepository.save(scheduling);
        return scheduleMapper.entityToDetailDto(updatedScheduling);
    }

    public void delete(Long id) {
        if (!schedulingRepository.existsById(id)) {
            throw new SchedulingNotFoundException("Agendamento não encontrado com o ID: " + id);
        }
        schedulingRepository.deleteById(id);
    }

    public SchedulingDetailResponseDTO findById(Long id) {
        Scheduling scheduling = schedulingRepository.findById(id)
                .orElseThrow(() -> new SchedulingNotFoundException("Agendamento não encontrado com o ID: " + id));
        return scheduleMapper.entityToDetailDto(scheduling);
    }

    public List<SchedulingDetailResponseDTO> findAll() {
        return schedulingRepository.findAll()
                .stream()
                .map(scheduleMapper::entityToDetailDto)
                .collect(Collectors.toList());
    }

    public void cancel(Long id) {
        Scheduling scheduling = schedulingRepository.findById(id)
                .orElseThrow(() -> new SchedulingNotFoundException("Agendamento não encontrado com o ID: " + id));

        if (scheduling.getScheduleStatus() == ScheduleStatus.FINISHED) {
            throw new IllegalStateException("Não é possível cancelar um agendamento que já foi finalizado.");
        }

        scheduling.setScheduleStatus(ScheduleStatus.CANCELED);
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
                requestDTO.getVetId(),
                ScheduleStatus.CANCELED
        );

        int availability = (int) Math.max(0, SHIFT_LIMIT - total);

        return new AvailableResponseDTO(requestDTO.getShift(), availability);
    }
}
