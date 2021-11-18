package dev.davivieira.framework.adapters.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import dev.davivieira.application.usecases.RouterNetworkUseCase;
import dev.davivieira.domain.entity.Router;
import dev.davivieira.framework.adapters.input.RouterNetworkAdapter;
import dev.davivieira.framework.adapters.output.file.mappers.RouterJsonFileMapper;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.*;
import java.net.URLDecoder;

public class RouterNetworkRestAdapter extends RouterNetworkAdapter {

    public RouterNetworkRestAdapter(RouterNetworkUseCase routerNetworkUseCase){
        this.routerNetworkUseCase = routerNetworkUseCase;
    }

    @Override
    public Router processRequest(Object requestParams){
        Map<String, String> params = new HashMap<>();
        if(requestParams instanceof HttpServer) {
            var httpserver = (HttpServer) requestParams;
            httpserver.createContext("/network/add", (exchange -> {
                if ("GET".equals(exchange.getRequestMethod())) {
                    var query = exchange.getRequestURI().getRawQuery();
                    httpParams(query, params);
                    router = this.addNetworkToRouter(params);
                    ObjectMapper mapper = new ObjectMapper();
                    var routerJson = mapper.writeValueAsString(RouterJsonFileMapper.toJson(router));
                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    exchange.sendResponseHeaders(200, routerJson.getBytes().length);
                    OutputStream output = exchange.getResponseBody();
                    output.write(routerJson.getBytes());
                    output.flush();
                } else {
                    exchange.sendResponseHeaders(405, -1);
                }
                exchange.close();
            }));
            httpserver.setExecutor(null);
            httpserver.start();
        }
        return router;
    }

    private void httpParams(String query, Map<String, String> params) {
        var noNameText = "Anonymous";
        var requestParams = Pattern.compile("&").splitAsStream(query)
                .map(s -> Arrays.copyOf(s.split("="), 2))
                .collect(groupingBy(s -> decode(s[0]), mapping(s -> decode(s[1]), toList())));
        var routerId = requestParams.getOrDefault("routerId", List.of(noNameText)).stream().findFirst().orElse(noNameText);
        params.put("routerId",routerId);
        var address = requestParams.getOrDefault("address", List.of(noNameText)).stream().findFirst().orElse(noNameText);
        params.put("address",address);
        var name = requestParams.getOrDefault("name", List.of(noNameText)).stream().findFirst().orElse(noNameText);
        params.put("name",name);
        var cidr = requestParams.getOrDefault("cidr", List.of(noNameText)).stream().findFirst().orElse(noNameText);
        params.put("cidr",cidr);
    }

    private static String decode(final String encoded) {
        try {
            return encoded == null ? null : URLDecoder.decode(encoded, "UTF-8");
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 is a required encoding", e);
        }
    }
}
