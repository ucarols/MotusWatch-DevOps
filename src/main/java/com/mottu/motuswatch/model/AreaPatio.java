package com.mottu.motuswatch.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AreaPatio {
    VERMELHO, AMARELO, ROXO, VERDE;

    @JsonCreator
    public static AreaPatio fromString(String value) {
        return AreaPatio.valueOf(value.toUpperCase());
    }
}
