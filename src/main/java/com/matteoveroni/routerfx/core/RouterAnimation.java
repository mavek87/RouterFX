package com.matteoveroni.routerfx.core;

/**
 * @Author Matteo Veroni
 */
public enum RouterAnimation {
    
    FADE_SHORT("fade", 1300.0),
    FADE_MEDIUM("fade", 2100.0),
    FADE_LONG("fade", 5000.0);

    private final String name;
    private final double duration;

    RouterAnimation(String name, double duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public double getDuration() {
        return duration;
    }
}

