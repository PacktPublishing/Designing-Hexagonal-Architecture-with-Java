package dev.davivieira.topologyinventory.framework.adapters.input.rest.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.davivieira.topologyinventory.domain.vo.Location;

import java.io.IOException;

public class LocationDeserializer extends StdDeserializer<Location> {

    public LocationDeserializer() {
        this(null);
    }

    public LocationDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Location deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        var address = node.get("address").asText();
        var city = node.get("city").asText();
        var state = node.get("state").asText();
        var zipCode = node.get("zipCode").intValue();
        var country = node.get("country").asText();
        var latitude = node.get("latitude").intValue();
        var longitude = node.get("longitude").intValue();

        return Location.builder()
                .address(address)
                .city(city)
                .state(state)
                .zipCode(zipCode)
                .country(country)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

    public static Location getLocation(JsonNode jsonNode) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Location.class, new LocationDeserializer());
        mapper.registerModule(module);
        return mapper.readValue(jsonNode.toString(), Location.class);
    }
}
