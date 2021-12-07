package com.example.seniorsafety.models;

public class ViewPort {
    private SouthWest southwest;
    private NorthEast northeast;

    public SouthWest getSouthwest() {
        return southwest;
    }

    public void setSouthwest(SouthWest southwest) {
        this.southwest = southwest;
    }

    public NorthEast getNortheast() {
        return northeast;
    }

    public void setNortheast(NorthEast northeast) {
        this.northeast = northeast;
    }

    @Override
    public String toString() {
        return "ClassPojo [southwest = " + southwest + ", northeast = " + northeast + "]";
    }
}
