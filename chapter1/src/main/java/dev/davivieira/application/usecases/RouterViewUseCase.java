package dev.davivieira.application.usecases;

import dev.davivieira.domain.Router;

import java.util.List;
import java.util.function.Predicate;

public interface RouterViewUseCase {

    List<Router> getRouters(Predicate<Router> filter);
}
