package dev.davivieira.topologyinventory.framework.adapters.output.h2.data;

import jakarta.persistence.Embeddable;

@Embeddable
public enum SwitchTypeData {
    LAYER2,
    LAYER3;
}
