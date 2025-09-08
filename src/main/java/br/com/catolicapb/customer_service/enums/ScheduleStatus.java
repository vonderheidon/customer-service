package br.com.catolicapb.customer_service.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ScheduleStatus {

    OPEN("Em aberto"),
    SERVICE("Em atendimento"),
    FINISHED("Finalizado"),
    CANCELED("Cancelado");

    private String value;

    ScheduleStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static ScheduleStatus fromJson(String value) {
        for (ScheduleStatus scheduleStatus : ScheduleStatus.values()) {
            if (scheduleStatus.value.equalsIgnoreCase(value)) {
                return scheduleStatus;
            }
        }
        throw new IllegalArgumentException("Status de agendamento inválido: " + value);
    }
}
