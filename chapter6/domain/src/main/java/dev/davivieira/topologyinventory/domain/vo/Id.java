package dev.davivieira.topologyinventory.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
public class Id {

    private final UUID id;

    private Id(UUID id) {
        this.id = id;
    }

    public static Id withId(String id) {
        return new Id(UUID.fromString(id));
    }

    public static Id withoutId() {
        return new Id(UUID.randomUUID());
    }
}
