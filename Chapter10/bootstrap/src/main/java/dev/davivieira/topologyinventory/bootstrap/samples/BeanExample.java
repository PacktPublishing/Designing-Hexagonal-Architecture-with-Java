package dev.davivieira.topologyinventory.bootstrap.samples;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BeanExample {
    public String simpleBean() {
        return "This is a simple bean";
    }
}
