package br.com.catolicapb.customer_service.constants;

public class ScheduleConstants {

    private ScheduleConstants() {}

    public static final Integer SHIFT_LIMIT = 3;
    public static final Integer STATUS_CREATED = 201;
    public static final Integer STATUS_OK = 200;
    public static final String SHIFT_LIMIT_MESSAGE = "Não há vagas disponíveis";
    public static final String SCHEDULE_CREATED_MESSAGE = "Agendamento realizado com sucesso";
    public static final String SCHEDULE_CANCELED_MESSAGE = "Agendamento cancelado com sucesso";
    public static final String SCHEDULE_DELETED_MESSAGE = "Agendamento excluído com sucesso";
}
