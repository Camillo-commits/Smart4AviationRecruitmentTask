package com.k.o.smart4aviation.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.HashMap;
import java.util.Map;

import static com.vaadin.flow.component.Tag.H2;

@Route(value = "about", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("About")
@CssImport("./views/about/about-view.css")
public class About extends HorizontalLayout {

    private Div databasePage;
    private Div frontendPage;
    private Div businessPage;

    public About(){
        addClassName("About");

        VerticalLayout layout = new VerticalLayout();
        layout.add(new VerticalLayout(new Text("Website and database accessible through created server are not representing any Smart4Aviation software.")));
        layout.add(new VerticalLayout(new Text("Elements mentioned above had been created for the purpose of recruitment process")));
        layout.add(new VerticalLayout(new Text("Designed and implemented by Kamil Osika")));
        layout.add(new VerticalLayout(new H1("Simplified documentation:")));
        layout.add(new VerticalLayout(new Text("Created server can be divided into three main components:")));

        Tab databaseTab = new Tab("Database");
        databasePage = new Div();
        createDatabasePage();
        databasePage.setVisible(false);

        Tab frontendTab = new Tab("Frontend");
        frontendPage = new Div();
        createFrontendPage();
        frontendPage.setVisible(false);

        Tab businessTab = new Tab("Business logic");
        businessPage = new Div();
        createBusinessPage();
        businessPage.setVisible(false);

        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(databaseTab,databasePage);
        tabsToPages.put(frontendTab,frontendPage);
        tabsToPages.put(businessTab,businessPage);

        Tabs tabs = new Tabs(databaseTab, frontendTab, businessTab);
        Div pages = new Div(databasePage, frontendPage, businessPage);

        tabs.setSelectedTab(null);

        tabs.addSelectedChangeListener(event -> {
            tabsToPages.values().forEach(page -> page.setVisible(false));
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
        });
        tabs.setFlexGrowForEnclosedTabs(1);

        layout.add(tabs);
        layout.add(pages);
        layout.setPadding(true);
        layout.setAlignItems(Alignment.STRETCH);
        add(layout);
    }
    private void createDatabasePage(){
        VerticalLayout dbLayout = new VerticalLayout();
        dbLayout.add(
                new HorizontalLayout(new H1("Database")),
                new HorizontalLayout(new Text("Server uses H2 database. It had been chosen because of it's safety features, like data encryption and small memory footprint.")),
                new HorizontalLayout(new Text("Currently database is set to clear everything on startup although this can be changed in application.properties file.")),
                new HorizontalLayout(new Text("Data can be imported through http POST requests. Sample requests will be placed in input directory.")),
                new HorizontalLayout(new Text("Connection to database is acquired by Spring JDBC, but all requests and queries are done via Spring DAO.")),
                new HorizontalLayout(new Text("To design database model Spring ORM support was used. This example is based on Hibernate ORM tool.")),
                new HorizontalLayout(new Text("Database had been divided into three tables:")),
                new HorizontalLayout(new Text("1. Baggage - where information about baggage for every flight is stored.")),
                new HorizontalLayout(new Text("2. Cargo - where information about cargo for every flight is stored.")),
                new HorizontalLayout(new Text("3. Flight - where information about flights is stored.")),
                new HorizontalLayout(new Text("All of these tables are connected via relation one to many or many to one.")),
                new HorizontalLayout(new Text("Because Cargo and Baggage data, that were generated did not contain unique id, one had been added and set to be autogenerated to avoid problems with primary key.")),

                new HorizontalLayout(new H1("Repositories")),
                new HorizontalLayout(new Text("Repositories are interfaces that are extending CrudRepository, which is parametrized by type of the objects that are going to be placed in database, as well as their ID.")),
                new HorizontalLayout(new Text("It allows to use simple methods to put, edit and retrieve data from the database."))
        );
        dbLayout.setPadding(true);
        databasePage.add(dbLayout);
    }
    private void createFrontendPage(){
        VerticalLayout frontLayout = new VerticalLayout();
        frontLayout.add(
                new HorizontalLayout(new H1("Frontend")),
                new HorizontalLayout(new Text("To create good looking, responsive frontend vaadin framework was used for website development.")),
                new HorizontalLayout(new Text("It allows to create webpages purely in Java, although JavaScript and Css styling had been used to create better looking frontend design.")),
                new HorizontalLayout(new Text("")),
                new HorizontalLayout(new Text("Disclaimer:")),
                new HorizontalLayout(new Text("JavaScript and Css styles had been generated, by vaadin.")),
                new HorizontalLayout(new Text("")),
                new HorizontalLayout(new Text("Each page derives from MainView class which defines parts like menu or top blue panel.")),
                new HorizontalLayout(new Text("Then each element on page is placed as an object of class in layouts. Those are used to group components.")),
                new HorizontalLayout(new Text("Then add(Component... components) function is used to add all components visible on the page.")),
                new HorizontalLayout(new Text("Each page contains annotations, providing css and Url mappings. Those are:")),
                new HorizontalLayout(new Text(" - @Route(value = \"URL_MAPPING\", layout = LAYOUT.class)")),
                new HorizontalLayout(new Text(" - @PageTitle(\"PAGE_TITLE\") ")),
                new HorizontalLayout(new Text(" - @CssImport(\"ROUTE_TO_CSS_FILE\")"))
        );
        frontLayout.setPadding(true);
        frontendPage.add(frontLayout);
    }

    private void createBusinessPage(){
        VerticalLayout businessLayout = new VerticalLayout();
        businessLayout.add(
                new HorizontalLayout(new H1("Flight number request")),
                new HorizontalLayout(new Text("Task was to provide information about cargo, baggage and total weight based upon flight number and date.")),
                new HorizontalLayout(new Text("On page with the same name as the task there is a text field for flight number, component for choosing the date, numeric fields for time and selection panel for choosing the unit of the result.")),
                new HorizontalLayout(new Text("Submission of the input was done by clicking Search button located at the bottom.")),
                new HorizontalLayout(new Text("All input fields are protected from users errors, informing them about their mistakes.")),
                new HorizontalLayout(new Text("If provided data is sufficient enough to make predictions about users will, programme will give suggestions, so that user does not need to provide perfect input.")),
                new HorizontalLayout(new Text("When processes above are complete, programme finds all baggage and cargo records regarding the flight.")),
                new HorizontalLayout(new Text("Then it sums all the weight, converting it beforehand to appropriate weight unit.")),
                new HorizontalLayout(new Text("After that results are shown to the user on new popup window.")),

                new HorizontalLayout(new H1("IATA request")),
                new HorizontalLayout(new Text("In this task programme had to provide information about number of arriving and departing flights from the airport.")),
                new HorizontalLayout(new Text("It also needed to calculate number of pieces of baggage that were arriving and departing from the facility.")),
                new HorizontalLayout(new Text("As an input user needed to provide the software with an IATA Airport Code and a date.")),
                new HorizontalLayout(new Text("For my understanding of the task, user needed to put period in between which calculations are going to be made.")),
                new HorizontalLayout(new Text("Because of that on page created for solving this task has:")),
                new HorizontalLayout(new Text(" - Selection window with IATA Airport Codes that are currently available in the database.")),
                new HorizontalLayout(new Text(" - Date and time windows similar to ones used in Flight number request task.")),
                new HorizontalLayout(new Text(" - Button used for submitting the input.")),
                new HorizontalLayout(new Text("All input components had been implemented with safety features like min/max values and requirement of placing the value.")),
                new HorizontalLayout(new Text("Because programme can use not completely full date data, it does not need to have time constraints filled in order to perform calculations.")),
                new HorizontalLayout(new Text("Statement above is applicable to both tasks.")),
                new HorizontalLayout(new Text("When software has enough input data, it finds flights that are arriving and departing from the airport grouping them by that category.")),
                new HorizontalLayout(new Text("Size of those two groups are going to be the part of the final calculation results.")),
                new HorizontalLayout(new Text("Then algorithm finds all baggage records in BAGGAGE table for each flight from particular group, adding number of pieces of luggage to one variable.")),
                new HorizontalLayout(new Text("When both flight groups have finished their calculations new popup window is created to show results of the calculations.")),
                new HorizontalLayout(new Text("In real solution it would be a good idea to make these calculations run in parallel, by implementing Runnable or Callable interface."))
        );
        businessLayout.setPadding(true);
        businessPage.add(businessLayout);
    }
}
