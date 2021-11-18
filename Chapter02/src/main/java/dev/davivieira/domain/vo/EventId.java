package dev.davivieira.domain.vo;

public class EventId {

    private final String id;

    private EventId(String id){
        this.id = id;
    }

    public static EventId of(String id){
        return new EventId(id);
    }

    @Override
    public String toString() {
        return "EventId{" +
                "id='" + id + '\'' +
                '}';
    }
}
