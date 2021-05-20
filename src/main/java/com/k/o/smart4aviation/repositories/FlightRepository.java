package com.k.o.smart4aviation.repositories;

import com.k.o.smart4aviation.models.Flight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends CrudRepository<Flight,Long> {
    public Flight findByFlightNumber(int flightNumber);
    public Flight findByFlightId(long flightId);
    public List<Flight> findALlByFlightNumberIsLike(int flightNumber);
    public List<Flight> findAllByDepartureDateIsLike(String date);
    public List<Flight> findAllByFlightNumberIsLikeAndDepartureDateIsLike(int flightNumber,String date);
    public List<Flight> findAllByFlightNumberIsLikeOrDepartureDateIsLike(int flightNumber,String date);
    public List<Flight> findAll();
    public List<Flight> findAllByArrivalAirportIATACodeAndDepartureDateBetween(char[] IATACode, String dateBegin, String dateEnd);
    public List<Flight> findAllByDepartureAirportIATACodeAndDepartureDateBetween(char[] IATACode,String dateBegin, String dateEnd);
    public List<Flight> findAllByDepartureDateBetween(String dateBegin, String dateEnd);
    public List<Flight> findAllByDepartureDateContaining(String date);
}
