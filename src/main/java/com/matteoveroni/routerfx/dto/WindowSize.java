package com.matteoveroni.routerfx.dto;

/**
 * @Author Matteo Veroni
 */
public class WindowSize {

    private final double width;
    private final double height;

    public WindowSize(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public String toString() {
        return "WindowSize(width=" + this.getWidth() + ", height=" + this.getHeight() + ")";
    }
}
