package br.com.catolicapb.customer_service.controller;

import br.com.catolicapb.customer_service.dto.*;
import br.com.catolicapb.customer_service.service.SchedulingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.catolicapb.customer_service.constants.ScheduleConstants.*;

@RestController
@RequestMapping("/api/v1/customer-service")
@RequiredArgsConstructor
public class CustomerServiceController {

    private final SchedulingService schedulingService;

    @PostMapping("/verify")
    public ResponseEntity<SchedulingResponseDTO> verifyAvailable(@Valid @RequestBody AvailableSchedulingRequestDTO requestDTO) {
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
    public ResponseEntity<ResponseDTO> create(@Valid @RequestBody ScheduleRequestDTO requestDTO) {
        schedulingService.save(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDTO.builder()
                        .httpCodeStatus(STATUS_CREATED)
                        .responseMessage(SCHEDULE_CREATED_MESSAGE)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {
        schedulingService.delete(id);
        var responseDTO = ResponseDTO.builder()
                .httpCodeStatus(STATUS_OK)
                .responseMessage(SCHEDULE_DELETED_MESSAGE)
                .build();
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchedulingDetailResponseDTO> update(@PathVariable Long id, @Valid @RequestBody SchedeUpdateRequestDTO requestDTO) {
        var updatedSchedulingDTO = schedulingService.update(id, requestDTO);
        return ResponseEntity.ok(updatedSchedulingDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchedulingDetailResponseDTO> findById(@PathVariable Long id) {
        var schedulingDTO = schedulingService.findById(id);
        return ResponseEntity.ok(schedulingDTO);
    }

    @GetMapping
    public ResponseEntity<List<SchedulingDetailResponseDTO>> findAll() {
        var schedulesDTO = schedulingService.findAll();
        return ResponseEntity.ok(schedulesDTO);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ResponseDTO> cancel(@PathVariable Long id) {
        schedulingService.cancel(id);
        var responseDTO = ResponseDTO.builder()
                .httpCodeStatus(STATUS_OK)
                .responseMessage(SCHEDULE_CANCELED_MESSAGE)
                .build();
        return ResponseEntity.ok(responseDTO);
    }
}
