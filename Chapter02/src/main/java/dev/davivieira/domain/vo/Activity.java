package dev.davivieira.domain.vo;

public class Activity {

    private final String srcHost;
    private final String dstHost;

    public Activity (String srcHost, String dstHost){
        this.srcHost = srcHost;
        this.dstHost = dstHost;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "srcHost='" + srcHost + '\'' +
                ", dstHost='" + dstHost + '\'' +
                '}';
    }
}
