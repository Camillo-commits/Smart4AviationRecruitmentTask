package com.k.o.smart4aviation.views;

import com.k.o.smart4aviation.models.Baggage;
import com.k.o.smart4aviation.models.Cargo;
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
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Route(value = "flight-number", layout = MainView.class)
@PageTitle("Flight number request")
@CssImport("./views/flight-number/flight-number-view.css")
public class FlightNumberRequest extends HorizontalLayout {
    private DatePicker datePicker;
    private IntegerField flightNumber;
    private Text flightNumDescr;
    private Text dateDescr;
    private Button searchButton;
    private Icon icon;
    private IntegerField timeH;
    private IntegerField timeM;
    private IntegerField timeS;
    private TextField zone;
    private Select<String> label;
    private final double conversionRatioLb2Kg = 0.454;

    private double cargoWeight;
    private double baggageWeight;
    private double totalWeight;

    public FlightNumberRequest(){
        addClassName("FlightNumberRequest");
        VerticalLayout layout = new VerticalLayout();

        HorizontalLayout flightNumLayout = new HorizontalLayout();
        flightNumber = new IntegerField();
        flightNumber.setMin(1000);
        flightNumber.setMax(9999);
        flightNumber.setLabel("Flight number");
        flightNumLayout.add(flightNumber);
        flightNumLayout.setPadding(true);

        HorizontalLayout dateLayout = new HorizontalLayout();
        datePicker = new DatePicker(LocalDate.now());
        datePicker.setLabel("Flight date");

        timeH = new IntegerField("Hour of departure","hh");
        timeM = new IntegerField("Minute of departure","mm");
        timeS = new IntegerField("Second of departure", "ss");
        zone = new TextField("Zone of departure (UTC)", "ex. -02:00");

        timeH.setMin(0);       timeH.setMax(23);
        timeM.setMin(0);       timeM.setMax(59);
        timeS.setMin(0);       timeS.setMax(59);
        dateLayout.add(datePicker, timeH, timeM, timeS, zone);
        dateLayout.setPadding(true);

        HorizontalLayout unitLayout = new HorizontalLayout();
        label = new Select<>();
        label.setItems("kg", "lb");
        label.setLabel("Set unit");
        label.setEmptySelectionAllowed(false);
        unitLayout.setPadding(true);
        unitLayout.add(label);

        HorizontalLayout searchLayout = new HorizontalLayout();
        searchLayout.add(new Text("  "));
        icon = new Icon(VaadinIcon.SEARCH);
        icon.setColor("blue");
        searchButton = new Button("Search" ,icon);
        searchLayout.add(searchButton);
        searchLayout.setPadding(true);
        searchLayout.setAlignItems(Alignment.STRETCH);

        searchButton.addClickListener(e ->{
            searchButtonFunction();
        });

        layout.setPadding(true);
        layout.setAlignItems(Alignment.START);
        layout.add(flightNumLayout, dateLayout, unitLayout, searchLayout);
        add(layout);
    }

    private boolean sum(List<Baggage> baggageList, List<Cargo> cargoList){
        try {
            for (Baggage b : baggageList) {
                //convert form lb to kg
                if (label.getValue().equals("kg") && b.getWeightUnit().equals("lb")) {
                    baggageWeight += b.getWeight() * conversionRatioLb2Kg;
                }
                //convert from kg to lb
                else if (label.getValue().equals("lb") && b.getWeightUnit().equals("kg")) {
                    baggageWeight += b.getWeight() * (1 / conversionRatioLb2Kg);
                } else
                    baggageWeight += b.getWeight();
            }

            for (Cargo c : cargoList) {
                //convert form lb to kg
                if (label.getValue().equals("kg") && c.getWeightUnit().equals("lb")) {
                    cargoWeight += c.getWeight() * conversionRatioLb2Kg;
                }
                //convert from kg to lb
                else if (label.getValue().equals("lb") && c.getWeightUnit().equals("kg")) {
                    cargoWeight += c.getWeight() * (1 / conversionRatioLb2Kg);
                } else
                    cargoWeight += c.getWeight();
            }
        }
        catch (NullPointerException npe){
            Dialog dialog = new Dialog(new Text("Please specify weight unit"));
            dialog.open();
            return false;
        }
        return true;
    }

    private void createResultDialog(){
        Dialog result = new Dialog();

        HorizontalLayout resultLayout = new HorizontalLayout(new Text("Search Results:"));

        BigDecimalField cargo = new BigDecimalField();
        cargo.setValue(BigDecimal.valueOf(cargoWeight));
        cargo.setWidth(100, Unit.PERCENTAGE);
        cargo.setLabel("Cargo weight:");
        cargo.setReadOnly(true);


        BigDecimalField baggage = new BigDecimalField();
        baggage.setValue(BigDecimal.valueOf(baggageWeight));
        baggage.setWidth(100, Unit.PERCENTAGE);
        baggage.setLabel("Baggage weight:");
        baggage.setReadOnly(true);


        BigDecimalField total = new BigDecimalField();
        total.setValue(BigDecimal.valueOf(totalWeight));
        total.setWidth(100, Unit.PERCENTAGE);
        total.setLabel("Total weight:");
        total.setReadOnly(true);

        HorizontalLayout cargoWeightLayout = new HorizontalLayout(cargo);
        HorizontalLayout baggageWeightLayout = new HorizontalLayout(baggage);
        HorizontalLayout totalWeightLayout = new HorizontalLayout(total);

        resultLayout.setAlignItems(Alignment.CENTER);
        cargoWeightLayout.setAlignItems(Alignment.CENTER);
        baggageWeightLayout.setAlignItems(Alignment.CENTER);
        totalWeightLayout.setAlignItems(Alignment.CENTER);

        result.add(resultLayout, cargoWeightLayout, baggageWeightLayout, totalWeightLayout);
        result.setResizable(true);
        result.open();
    }

    private void searchButtonFunction(){
        String dateString = DateRetriever.retrieveDateAndTime(datePicker, timeH, timeM, timeS, zone);
        if(dateString.equals("")){
            Dialog dialog = new Dialog(new Text("Date or time input incorrect"));
            dialog.open();
            return;
        }

        List<Baggage> baggageList = new ArrayList<>();
        List<Cargo> cargoList = new ArrayList<>();
        if(flightNumber.getValue() != null) {
            baggageList = baggageRepository.findAllByFlight_FlightNumberAndAndFlight_DepartureDate(flightNumber.getValue(), dateString);
            cargoList = cargoRepository.findAllByFlight_FlightNumberAndAndFlight_DepartureDate(flightNumber.getValue(), dateString);
        }

        cargoWeight = 0;
        baggageWeight = 0;
        boolean errorOccurred = false;
        try {
            if (baggageList.size() == 0 || cargoList.size() == 0) {
                errorOccurred = true;
                Dialog error = new Dialog();
                VerticalLayout errorLayout = new VerticalLayout();
                errorLayout.add(new Text("Incorrect input data. Did you mean one of flights below?"));
                Select<Flight> flightLabel = new Select<>();
                List<Flight> flights = new ArrayList<>();
                if(flightNumber.getValue() != null) {
                    flights = flightRepository.findAllByFlightNumberIsLikeOrDepartureDateIsLike(flightNumber.getValue(), dateString);
                }else{
                    flights = flightRepository.findAllByDepartureDateIsLike(dateString);
                    if(flights.size() == 0){
                        flights = flightRepository.findAllByDepartureDateContaining(DateRetriever.retrieveDate(datePicker));
                    }
                }
                flightLabel.setItems(flights);
                flightLabel.setLabel("Choose appropriate flight");
                errorLayout.add(flightLabel);
                Button ok = new Button("Calculate");
                ok.addClickListener(ev -> {
                    try {
                        Flight selectedFlight = flightLabel.getValue();
                        List<Baggage> baggageList2 = baggageRepository.findAllByFlight_FlightNumberAndAndFlight_DepartureDate(selectedFlight.getFlightNumber(), selectedFlight.getDepartureDate());
                        List<Cargo> cargoList2 = cargoRepository.findAllByFlight_FlightNumberAndAndFlight_DepartureDate(selectedFlight.getFlightNumber(), selectedFlight.getDepartureDate());

                        if (!sum(baggageList2, cargoList2)) {
                            return;
                        }

                        totalWeight = cargoWeight + baggageWeight;
                        createResultDialog();
                    }
                    catch (NullPointerException npe){
                        Dialog dialog = new Dialog(new Text("Flight not selected"));
                        dialog.open();
                    }
                    catch (Exception ex){
                        Dialog dialog = new Dialog(new Text("Error occurred. Please try again later"));
                        dialog.open();
                    }
                });
                errorLayout.add(ok);
                errorLayout.add(new Text("If no flights are visible on the list please provide more detailed information about the flight"));

                error.add(errorLayout);
                error.open();
            }
            if (errorOccurred) {
                return;
            }

            if(!sum(baggageList, cargoList))
                return;
            totalWeight = cargoWeight + baggageWeight;
            createResultDialog();
        }
        catch (Exception exception){
            Dialog dialog = new Dialog(new Text("Error occurred. Please try again later"));
            dialog.open();
        }

    }


    @Autowired
    private BaggageRepository baggageRepository;
    @Autowired
    private CargoRepository cargoRepository;
    @Autowired
    private FlightRepository flightRepository;

}
