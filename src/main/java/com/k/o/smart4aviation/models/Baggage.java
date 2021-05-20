package com.k.o.smart4aviation.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Baggage {
    @Id
    @GeneratedValue
    private long baggageId;

    //used as local id that is fetched through input data
    private int id;
    private int weight;
    private int pieces;
    private String weightUnit;

    @ManyToOne
    private Flight flight;

    public Baggage() {
    }

    public long getBaggageId() {
        return baggageId;
    }

    public void setBaggageId(long baggageId) {
        this.baggageId = baggageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }
}
