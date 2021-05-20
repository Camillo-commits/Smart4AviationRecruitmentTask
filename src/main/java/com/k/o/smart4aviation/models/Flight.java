package com.k.o.smart4aviation.models;

import javax.persistence.*;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Set;

@Entity
public class Flight {
    @Id
    private long flightId;
    private int flightNumber;
    private char[] departureAirportIATACode;
    private char[] arrivalAirportIATACode;
    private String departureDate;

    @OneToMany
    private Set<Baggage> baggages;

    @OneToMany
    private Set<Cargo> cargos;

    public Flight() {
    }

    public long getFlightId() {
        return flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public char[] getDepartureAirportIATACode() {
        return departureAirportIATACode;
    }

    public void setDepartureAirportIATACode(char[] departureAirportIATACode) {
        this.departureAirportIATACode = departureAirportIATACode;
    }

    public char[] getArrivalAirportIATACode() {
        return arrivalAirportIATACode;
    }

    public void setArrivalAirportIATACode(char[] arrivalAirportIATACode) {
        this.arrivalAirportIATACode = arrivalAirportIATACode;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public Set<Baggage> getBaggages() {
        return baggages;
    }

    public void setBaggages(Set<Baggage> baggages) {
        this.baggages = baggages;
    }

    public Set<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(Set<Cargo> cargos) {
        this.cargos = cargos;
    }

    @Override
    public String toString() {
        return  "flightNumber=" + flightNumber + "\n" +
                ", departureAirportIATACode=" + String.valueOf(Arrays.toString(departureAirportIATACode)) + "\n" +
                ", arrivalAirportIATACode=" + String.valueOf(Arrays.toString(arrivalAirportIATACode)) + "\n" +
                ", departureDate='" + departureDate ;
    }
}
