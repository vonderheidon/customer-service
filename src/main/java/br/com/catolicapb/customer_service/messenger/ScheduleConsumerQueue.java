package br.com.catolicapb.customer_service.messenger;

import br.com.catolicapb.customer_service.dto.ScheduleRequestDTO;
import br.com.catolicapb.customer_service.messenger.constants.RabbitConstants;
import br.com.catolicapb.customer_service.service.SchedulingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.jmx.export.metadata.InvalidMetadataException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class ScheduleConsumerQueue {

    private final ObjectMapper mapper;
    private final SchedulingService service;

    @RabbitListener(queues = RabbitConstants.SCHEDULE_RABBIT_QUEUE_NAME)
    public void processMessage(Message message) {
        var scheduleDTO = convertMessage(message);
        log.info("m=processMessage, menssagem recebida da fila: {}", scheduleDTO);
        service.save(scheduleDTO);
    }

    private ScheduleRequestDTO convertMessage(Message message) {
        try {
            return mapper.readValue(message.getBody(), ScheduleRequestDTO.class);
        } catch (Exception ex) {
            throw new InvalidMetadataException(ex.getMessage());
        }
    }
}
