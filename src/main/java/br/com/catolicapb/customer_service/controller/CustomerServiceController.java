package br.com.catolicapb.customer_service.controller;

import br.com.catolicapb.customer_service.dto.AvailableSchedulingRequestDTO;
import br.com.catolicapb.customer_service.dto.ResponseDTO;
import br.com.catolicapb.customer_service.dto.ScheduleRequestDTO;
import br.com.catolicapb.customer_service.dto.SchedulingResponseDTO;
import br.com.catolicapb.customer_service.service.SchedulingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.catolicapb.customer_service.constants.ScheduleConstants.SCHEDULE_CREATED_MESSAGE;
import static br.com.catolicapb.customer_service.constants.ScheduleConstants.STATUS_CREATED;

@RestController
@RequestMapping("/api/v1/customer-service")
@RequiredArgsConstructor
public class CustomerServiceController {

    private final SchedulingService schedulingService;

    @PostMapping("/verify")
    public ResponseEntity<SchedulingResponseDTO> verifyAvailable(@RequestBody AvailableSchedulingRequestDTO requestDTO) {
        var availableResult = schedulingService.validateSchedulingAvailable(requestDTO);
        var scheduleResponseDTO = SchedulingResponseDTO
                .builder()
                .vetId(requestDTO.getVetId())
                .availableResponseDTO(availableResult)
                .dateSchedule(requestDTO.getDateSchedule())
                .build();
        return ResponseEntity.ok(scheduleResponseDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> create(@RequestBody ScheduleRequestDTO requestDTO) {
        schedulingService.save(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDTO.builder()
                        .httpCodeStatus(STATUS_CREATED)
                        .responseMessage(SCHEDULE_CREATED_MESSAGE)
                        .build());
    }
}
