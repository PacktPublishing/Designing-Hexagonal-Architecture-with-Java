package dev.davivieira.topologyinventory.framework.adapters.input.rest.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Network;

import java.io.IOException;

public class NetworkDeserializer extends StdDeserializer<Network> {

    public NetworkDeserializer() {
        this(null);
    }

    public NetworkDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Network deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        var networkAddress = node.get("networkAddress").get("ipAddress").asText();
        var networkName = node.get("networkName").asText();
        var networkCidr = node.get("networkCidr").asInt();
        return Network.builder()
                .networkAddress(IP.fromAddress(networkAddress))
                .networkName(networkName)
                .networkCidr(networkCidr)
                .build();
    }

    public static Network getNetworkDeserialized(String jsonStr) throws IOException {
        var mapper = new ObjectMapper();
        var module = new SimpleModule();
        module.addDeserializer(Network.class, new NetworkDeserializer());
        mapper.registerModule(module);
        var network = mapper.readValue(jsonStr, Network.class);
        return network;
    }
}
