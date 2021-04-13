package dev.davivieira.domain;

public class RouterId {

    private String id;

    private RouterId(String id){
        this.id = id;
    }

    public static RouterId of(String id){
        return new RouterId(id);
    }

    @Override
    public String toString() {
        return "RouterId{" +
                "id='" + id + '\'' +
                '}';
    }
}
