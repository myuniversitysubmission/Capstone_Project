package com.wastesystem.storage;

public class WasteItem {

    public enum Type {
        PLASTIC, METAL, PAPER, ORGANIC
    }

    private Type type;
    private double weight;

    public WasteItem(Type type, double weight) {
        this.type = type;
        this.weight = weight;
    }

    public Type getType() {
        return type;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return type + " (" + weight + " kg)";
    }
}



////WasteItem represents a waste object (plastic, metal, etc.)