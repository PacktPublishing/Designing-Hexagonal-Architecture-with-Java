package dev.davivieira.topologyinventory.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Location {

    private String address;
    private String city;
    private String state;
    private int zipCode;
    private String country;

    private float latitude;
    private float longitude;
}
