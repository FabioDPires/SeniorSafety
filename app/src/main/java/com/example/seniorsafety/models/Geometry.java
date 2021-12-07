package com.example.seniorsafety.models;

public class Geometry {
    private ViewPort viewport;
    private Location location;

    public ViewPort getViewport() {
        return viewport;
    }

    public void setViewport(ViewPort viewport) {
        this.viewport = viewport;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "ClassPojo [viewport = " + viewport + ", location = " + location + "]";
    }
}
