package dev.davivieira.application.usecases;

import dev.davivieira.domain.Router;
import dev.davivieira.domain.Type;

import java.util.List;

public interface RouterViewUseCase {

    List<Router> getRelatedRouters(RelatedRoutersCommand relatedRoutersCommand);

    class RelatedRoutersCommand {

        private Type type;

        public RelatedRoutersCommand(String type){
            this.type = Type.valueOf(type);
        }

        public Type getType() {
            return type;
        }
    }
}
