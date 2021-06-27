package dev.davivieira.topologyinventory.domain.vo;

public class Activity {

    private String srcHost;
    private String dstHost;

    public Activity (String srcHost, String dstHost){
        this.srcHost = srcHost;
        this.dstHost = dstHost;
    }

    public String retrieveSrcHost(){
        return this.srcHost;
    }
}
