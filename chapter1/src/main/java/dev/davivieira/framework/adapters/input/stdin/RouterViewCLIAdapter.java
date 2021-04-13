package dev.davivieira.framework.adapters.input.stdin;

import dev.davivieira.application.ports.input.RouterViewInputPort;
import dev.davivieira.application.usecases.RouterViewUseCase;
import dev.davivieira.application.usecases.RouterViewUseCase.RelatedRoutersCommand;
import dev.davivieira.domain.Router;
import dev.davivieira.framework.adapters.output.file.RouterViewFileAdapter;

import java.util.List;

public class RouterViewCLIAdapter {

    RouterViewUseCase routerViewUseCase;

    public RouterViewCLIAdapter(){
        setAdapters();
    }

    public List<Router> obtainRelatedRouters(String type) {
        RelatedRoutersCommand relatedRoutersCommand = new RelatedRoutersCommand(type);
        return routerViewUseCase.getRelatedRouters(relatedRoutersCommand);
    }

    private void setAdapters(){
        this.routerViewUseCase = new RouterViewInputPort(RouterViewFileAdapter.getInstance());
    }
}