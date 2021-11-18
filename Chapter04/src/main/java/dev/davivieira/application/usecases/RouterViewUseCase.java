package dev.davivieira.application.usecases;

import dev.davivieira.domain.entity.Router;
import dev.davivieira.domain.vo.RouterType;

import java.util.List;

public interface RouterViewUseCase {

    List<Router> getRelatedRouters(RelatedRoutersCommand relatedRoutersCommand);

    class RelatedRoutersCommand {

        private RouterType type;

        public RelatedRoutersCommand(String type){
            this.type = RouterType.valueOf(type);
        }

        public RouterType getType() {
            return type;
        }
    }
}
