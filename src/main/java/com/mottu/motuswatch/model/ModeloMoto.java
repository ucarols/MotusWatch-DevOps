package com.mottu.motuswatch.model;

public enum ModeloMoto {
    MOTTU_POP("Mottu Pop"),
    MOTTU_E("Mottu-e"),
    MOTTU_SPORT("Mottu Sport");

    private final String displayName;

    ModeloMoto(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
