package com.k.o.smart4aviation.views;

import com.k.o.smart4aviation.models.Baggage;
import com.k.o.smart4aviation.models.Flight;
import com.k.o.smart4aviation.repositories.BaggageRepository;
import com.k.o.smart4aviation.repositories.CargoRepository;
import com.k.o.smart4aviation.repositories.FlightRepository;
import com.k.o.smart4aviation.tools.DateRetriever;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Route(value = "iata", layout = MainView.class)
@PageTitle("IATA request")
@CssImport("./views/IATA/iata-view.css")
public class IATARequest extends HorizontalLayout {
    private DatePicker datePickerBegin;
    private DatePicker datePickerEnd;
    private Select<String> iataSelect;
    private IntegerField timeHBegin;
    private IntegerField timeMBegin;
    private IntegerField timeSBegin;
    private TextField zoneBegin;
    private IntegerField timeHEnd;
    private IntegerField timeMEnd;
    private IntegerField timeSEnd;
    private TextField zoneEnd;
    private Icon icon;

    private int numOfDepartures;
    private int numOfArrivals;
    private int numOfBaggageArriving;
    private int numOfBaggageDeparting;

    public IATARequest(){
        addClassName("IATARequest");
        VerticalLayout layout = new VerticalLayout();
        iataSelect = new Select<>();
        iataSelect.setLabel("IATA Airport Code");

        numOfBaggageArriving = 0;
        numOfDepartures = 0;
        numOfArrivals = 0;
        numOfBaggageDeparting = 0;

        HorizontalLayout iataLayout = new HorizontalLayout();
        Set<String> airportCodes = new HashSet<>();

        iataSelect.setItems(airportCodes);
        iataLayout.add(iataSelect);
        Button downloadCodes = new Button();
        downloadCodes.setIcon(new Icon(VaadinIcon.REFRESH));
        downloadCodes.addClickListener(ev -> {
            List<Flight> flightList = flightRepository.findAll();
            for(Flight f : flightList){
                if(!airportCodes.contains(f.getArrivalAirportIATACode())){
                    airportCodes.add(String.copyValueOf(f.getArrivalAirportIATACode()));
                }
                if(!airportCodes.contains(f.getDepartureAirportIATACode())){
                    airportCodes.add(String.copyValueOf(f.getDepartureAirportIATACode()));
                }
            }
            iataSelect.setItems(airportCodes);
        });
        iataLayout.add(downloadCodes);
        iataLayout.setPadding(true);

        HorizontalLayout descr1 = new HorizontalLayout();
        descr1.add(new Text("Date from:"));
        descr1.setPadding(true);

        HorizontalLayout dateLayoutBegin = new HorizontalLayout();
        datePickerBegin = new DatePicker(LocalDate.now());
        datePickerBegin.setLabel("Flight date");
        timeHBegin = new IntegerField("Hour of departure","hh");
        timeMBegin = new IntegerField("Minute of departure","mm");
        timeSBegin = new IntegerField("Second of departure", "ss");
        zoneBegin = new TextField("Zone of departure (UTC)", "ex. -02:00");

        timeHBegin.setMin(0);       timeHBegin.setMax(23);
        timeMBegin.setMin(0);       timeMBegin.setMax(59);
        timeSBegin.setMin(0);       timeSBegin.setMax(59);
        dateLayoutBegin.add(datePickerBegin, timeHBegin, timeMBegin, timeSBegin, zoneBegin);
        dateLayoutBegin.setPadding(true);

        HorizontalLayout descr2 = new HorizontalLayout();
        descr2.add(new Text("To:"));
        descr2.setPadding(true);

        HorizontalLayout dateLayoutEnd = new HorizontalLayout();
        datePickerEnd = new DatePicker(LocalDate.now());
        datePickerEnd.setLabel("Flight date");
        timeHEnd = new IntegerField("Hour of departure","hh");
        timeMEnd = new IntegerField("Minute of departure","mm");
        timeSEnd = new IntegerField("Second of departure", "ss");
        zoneEnd = new TextField("Zone of departure (UTC)", "ex. -02:00");

        timeHEnd.setMin(0);       timeHEnd.setMax(23);
        timeMEnd.setMin(0);       timeMEnd.setMax(59);
        timeSEnd.setMin(0);       timeSEnd.setMax(59);
        dateLayoutEnd.add(datePickerEnd, timeHEnd, timeMEnd, timeSEnd, zoneEnd);
        dateLayoutEnd.setPadding(true);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        icon = new Icon(VaadinIcon.SEARCH);
        icon.setColor("blue");
        Button searchButton = new Button("Search", icon);
        searchButton.addClickListener(e ->{
           searchButtonFunction();
        });
        buttonLayout.add(searchButton);
        buttonLayout.setPadding(true);

        layout.add(iataLayout, descr1, dateLayoutBegin, descr2, dateLayoutEnd, buttonLayout);
        add(layout);
    }

    private void createResultDialog(){
        Dialog result = new Dialog();
        //result.setWidth(50, Unit.PERCENTAGE);
        VerticalLayout layout = new VerticalLayout();
        layout.add(new Text("Search Results"));

        IntegerField departingFlights = new IntegerField("Number of departing flights");
        departingFlights.setWidth(100, Unit.PERCENTAGE);
        departingFlights.setValue(numOfDepartures);
        departingFlights.setReadOnly(true);

        IntegerField baggageDeparting = new IntegerField("Number of departing baggage");
        baggageDeparting.setWidth(100, Unit.PERCENTAGE);
        baggageDeparting.setValue(numOfBaggageDeparting);
        baggageDeparting.setReadOnly(true);

        IntegerField arrivingFlights = new IntegerField("Number of arriving flights");
        arrivingFlights.setWidth(100, Unit.PERCENTAGE);
        arrivingFlights.setValue(numOfArrivals);
        arrivingFlights.setReadOnly(true);

        IntegerField baggageArriving = new IntegerField("Number of arriving baggage");
        baggageArriving.setWidth(100, Unit.PERCENTAGE);
        baggageArriving.setValue(numOfBaggageArriving);
        baggageArriving.setReadOnly(true);

        layout.add(departingFlights,baggageDeparting,arrivingFlights,baggageArriving);
        layout.setAlignItems(Alignment.CENTER);
        layout.setWidth(100,Unit.PERCENTAGE);
        layout.setPadding(true);
        result.setResizable(true);
        result.add(layout);
        result.open();
    }

    private void clearCounters(){
        numOfBaggageDeparting = 0;
        numOfBaggageArriving = 0;
        numOfArrivals = 0;
        numOfDepartures = 0;
    }

    private void searchButtonFunction(){
        clearCounters();

        String dateStringBegin = DateRetriever.retrieveDateAndTime(datePickerBegin, timeHBegin, timeMBegin, timeSBegin, zoneBegin);
        String dateStringEnd = DateRetriever.retrieveDateAndTime(datePickerEnd,timeHEnd,timeMEnd,timeSEnd,zoneEnd);
        if(dateStringBegin.equals("") || dateStringEnd.equals("")){
            Dialog dialog = new Dialog(new Text("Date or time input incorrect"));
            dialog.open();
            return;
        }
        List<Flight> flightsArriving = new ArrayList<>();
        List<Flight> flightsDeparting = new ArrayList<>();

        if(iataSelect.getValue() != null) {
            String iataCode = iataSelect.getValue();
            flightsArriving = flightRepository.findAllByArrivalAirportIATACodeAndDepartureDateBetween(iataCode.toCharArray(), dateStringBegin, dateStringEnd);
            flightsDeparting = flightRepository.findAllByDepartureAirportIATACodeAndDepartureDateBetween(iataCode.toCharArray(), dateStringBegin, dateStringEnd);
        }
        else{
            Dialog dialog = new Dialog(new Text("Please select the Airport"));
            dialog.open();
            return;
        }
        for(Flight f : flightsArriving){
            List<Baggage> baggageArriving = baggageRepository.findAllByFlight(f);
            for(Baggage b : baggageArriving){
                numOfBaggageArriving += b.getPieces();
            }
        }
        for(Flight f : flightsDeparting){
            List<Baggage> baggageDeparting = baggageRepository.findAllByFlight(f);
            for(Baggage b : baggageDeparting){
                numOfBaggageDeparting += b.getPieces();
            }
        }

        numOfArrivals = flightsArriving.size();
        numOfDepartures = flightsDeparting.size();

        createResultDialog();

    }

    @Autowired
    private BaggageRepository baggageRepository;
    @Autowired
    private FlightRepository flightRepository;

}
