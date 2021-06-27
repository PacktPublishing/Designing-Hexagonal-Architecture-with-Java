package dev.davivieira.topologyinventory.domain.vo;

public class EventId {
    private String id;

    private EventId(String id){
        this.id = id;
    }

    public static EventId of(String id){
        return new EventId(id);
    }
}
