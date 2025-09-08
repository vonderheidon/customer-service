package br.com.catolicapb.customer_service.enums;

import com.fasterxml.jackson.annotation.JsonValue;

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

    public static ScheduleShift fromJson(String value) {
        for (ScheduleShift scheduleShift : ScheduleShift.values()) {
            if (scheduleShift.value.equalsIgnoreCase(value)) {
                return scheduleShift;
            }
        }
        throw new IllegalArgumentException("Turno do agendamento inválido: " + value);
    }
}
