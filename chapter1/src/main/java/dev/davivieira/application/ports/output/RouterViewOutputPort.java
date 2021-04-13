package dev.davivieira.application.ports.output;

import dev.davivieira.domain.Router;

import java.util.List;

public interface RouterViewOutputPort {

    List<Router> fetchRelatedRouters();
}
