package br.com.catolicapb.customer_service.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Objects;

public enum ScheduleShift {

    MORNING("Manha"),
    AFTERNOON("Tarde");

    private String value;

    ScheduleShift(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ScheduleShift fromJson(String value) {
        if (Objects.isNull(value) || value.isEmpty()) {
            throw new IllegalArgumentException("Turno do agendamento não pode ser nulo ou vazio");
        }
        for (ScheduleShift scheduleShift : ScheduleShift.values()) {
            if (scheduleShift.value.equalsIgnoreCase(value)) {
                return scheduleShift;
            }
        }
        throw new IllegalArgumentException("Turno do agendamento inválido: " + value + ". Valores aceitos: Manha, Tarde.");
    }
}
