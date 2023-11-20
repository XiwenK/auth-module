package com.manunin.score.model;

public enum ResourceType {
    FLAT("Flat"),
    HOUSE("House"),
    ROOM("Room"),
    OFFICE("Office"),
    GARAGE("Garage"),
    PARKING("Parking"),
    LAND("Land"),
    BED("Bed"),
    CAR("Car"),
    BOAT("Boat"),
    AIRPLANE("Airplane"),
    HELICOPTER("Helicopter"),
    BICYCLE("Bicycle"),
    MOTORCYCLE("Motorcycle"),
    SCOOTER("Scooter"),
    TRUCK("Truck"),
    BUS("Bus"),
    TRAIN("Train"),
    WORK_PLACE("Work place");

    final String name;

    ResourceType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
