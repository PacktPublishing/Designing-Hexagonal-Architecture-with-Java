package dev.davivieira.domain.vo;

import dev.davivieira.domain.entity.Event;

public class EventId {
    private String id;

    private EventId(String id){
        this.id = id;
    }

    public static EventId of(String id){
        return new EventId(id);
    }
}
