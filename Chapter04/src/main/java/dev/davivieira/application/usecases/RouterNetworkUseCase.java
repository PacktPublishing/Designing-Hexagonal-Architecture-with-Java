package dev.davivieira.application.usecases;

import dev.davivieira.domain.entity.Router;
import dev.davivieira.domain.vo.Network;
import dev.davivieira.domain.vo.RouterId;

public interface RouterNetworkUseCase {

    Router addNetworkToRouter(RouterId routerId, Network network);
}
