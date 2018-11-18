package panes;

import com.mysql.jdbc.StringUtils;
import form.VehicleChoice;
import javabean.CustomerVehicles;
import javabean.Customers;
import javabean.Vehicles;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import tables.CustomerVehiclesTable;
import tables.CustomersTable;
import tables.VehiclesTable;

import java.util.*;

import static main.Const.TEXTFIELD_WIDTH_SIZE;

/**
 *
 * Contains edit customer information gui
 *
 * @author James DiNovo
 * @date 17.11.2018
 * @version 1.0
 */
public class EditCustInfoPane extends BorderPane {
    //Importing the vehicleMap
    private Map<String, List<String>> vehicleMap = VehicleChoice.getVehicleModel();

    //ComboBoxes for the form
    private ComboBox<String> comboBrand = new ComboBox<>();
    private ComboBox<String> comboModel = new ComboBox<>();

    private Customers customer = new Customers();
    private Vehicles vehicle = new Vehicles();
    private ArrayList<Customers> customers;
    private ArrayList<CustomerVehicles> customerVehicles = new ArrayList<>();
    private ArrayList<Vehicles> vehicles = new ArrayList<>();

    private ListView<Vehicles> vehicleListView = new ListView<>();

    public EditCustInfoPane() {

        //declaring variables
        Text warning = new Text("Error!");
        warning.setVisible(false);
        warning.setFill(Color.RED);

        //get access to table classes
        CustomersTable custTable = new CustomersTable();
        VehiclesTable vehTable = new VehiclesTable();
        CustomerVehiclesTable custVehTable = new CustomerVehiclesTable();

        customers = custTable.getAllCustomers();

        //create and show pop up to get phone number to query db with
        TextInputDialog dialog = new TextInputDialog("(555)555-5555");
        dialog.setTitle("Find Customer");
        dialog.setHeaderText("Enter the customer's phone number and click OK to find their information.\nClick CANCEL to see all customers.");
        dialog.setContentText("Phone Number:");

        dialog.showAndWait().ifPresent(n-> {
            final String number = n.trim();
            System.out.println("phone number: " + number);
            customers.clear();
            custTable.getAllCustomers().forEach(e -> {
                if (e.getPhoneNumber().equals(number)) {
                    customers.add(e);
                }
            });
        });

        TableView<Customers> tableView = new TableView<>();
        tableView.setItems(FXCollections.observableArrayList(customers));

        tableView.setEditable(false);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setFixedCellSize(25);
        tableView.setPrefHeight(200);

        TableColumn<Customers, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Customers, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Customers, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Customers, String> cityCol = new TableColumn<>("City");
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));

        TableColumn<Customers, String> postalCol = new TableColumn<>("Postal Code");
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        TableColumn<Customers, String> phoneCol = new TableColumn<>("Phone Number");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn<Customers, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        tableView.getColumns().addAll(firstNameCol, lastNameCol, addressCol, cityCol, postalCol, phoneCol, emailCol);

        /**********************************************************
         * EDIT FORM
         ***********************************************************/

        HBox hBox = new HBox();
        VBox updateCustomerBox = new VBox();
        VBox addVehicleBox = new VBox();
        VBox vehicleList = new VBox();

        //Customer info title
        Text customerInfo = new Text("Customer Information");
        customerInfo.setFont(Font.font("Times New Roman", 20));

        //First Name Label
        Label firstNameText = new Label("First Name:");
        firstNameText.setFont(Font.font("Times New Roman", 16));

        //First Name TextField
        TextField firstName = new TextField();
        firstName.setMaxWidth(TEXTFIELD_WIDTH_SIZE);


        //Last Name Label
        Label lastNameText = new Label("Last Name:");
        lastNameText.setFont(Font.font("Times New Roman", 16));

        //Last Name textField
        TextField lastName = new TextField();
        lastName.setMaxWidth(TEXTFIELD_WIDTH_SIZE);


        //Address Label
        Label addressText = new Label("Address:");
        addressText.setFont(Font.font("Times New Roman", 16));

        //Address Textfield
        TextField address = new TextField();
        address.setMaxWidth(TEXTFIELD_WIDTH_SIZE);


        //City Label
        Label cityText = new Label("City:");
        cityText.setFont(Font.font("Times New Roman", 16));

        //City Textfield
        TextField city = new TextField();
        city.setMaxWidth(TEXTFIELD_WIDTH_SIZE);

        //Email Label
        Label emailText = new Label("Email:");
        emailText.setFont(Font.font("Times New Roman", 16));

        //Email Textfield
        TextField email = new TextField();
        email.setMaxWidth(TEXTFIELD_WIDTH_SIZE);


        //Postal Code Label
        Label postalCodeText = new Label("Postal Code:");
        postalCodeText.setFont(Font.font("Times New Roman", 16));

        //Postal Code Textfield
        TextField postalCode = new TextField();
        postalCode.setMaxWidth(TEXTFIELD_WIDTH_SIZE);


        //Phone number Label
        Label phoneNumText = new Label("Phone Number:");
        phoneNumText.setFont(Font.font("Times New Roman", 16));

        //Phone number textfield
        TextField phoneNum = new TextField();
        phoneNum.setMaxWidth(TEXTFIELD_WIDTH_SIZE);

        //Vehicle info title
        Text vehicleInfo = new Text("Add New Vehicle");
        vehicleInfo.setFont(Font.font("Times New Roman", 20));

        //Vin num Label
        Label vinNumText = new Label("VIN Number:");
        vinNumText.setFont(Font.font("Times New Roman", 16));

        //Vin Number textfield
        TextField vinNum = new TextField();
        vinNum.setMaxWidth(TEXTFIELD_WIDTH_SIZE);

        //Brand label
        Label brandText = new Label("Brand:");
        brandText.setFont(Font.font("Times New Roman", 16));

        //Brand ComboBox
        comboBrand.setMaxWidth(TEXTFIELD_WIDTH_SIZE);
        //Set the drop down menu to the vehicleMap's key values
        comboBrand.setItems(FXCollections.observableArrayList(vehicleMap.keySet()));
        comboModel.setOnMouseClicked(e->{
            for (Map.Entry<String, List<String>> pair : vehicleMap.entrySet()) {
                if(pair.getKey().equals(comboBrand.getValue())) {
                    comboModel.setItems(FXCollections.observableList(pair.getValue()));
                }
            }
        });

        //Model Label
        Label modelText = new Label("Model:");
        modelText.setFont(Font.font("Times New Roman", 16));

        //Model ComboBox
        comboModel.setMaxWidth(TEXTFIELD_WIDTH_SIZE);


        //Year Text
        Label yearText = new Label("Year:");
        yearText.setFont(Font.font("Times New Roman", 16));

        TextField year = new TextField();
        year.setMaxWidth(TEXTFIELD_WIDTH_SIZE);

        //Email
        Label kilometersText = new Label("Kilometers:");
        kilometersText.setFont(Font.font("Times New Roman", 16));

        TextField kilometers = new TextField();
        kilometers.setMaxWidth(TEXTFIELD_WIDTH_SIZE);

        Button updateCust = new Button("Update Info");
        updateCust.setOnAction(e-> {
            emailText.setTextFill(Color.BLACK);
            postalCodeText.setTextFill(Color.BLACK);
            phoneNumText.setTextFill(Color.BLACK);
            if (!email.getText().matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
                warning.setText("Please make sure email follows example@company.com");
                warning.setVisible(true);
                emailText.setTextFill(Color.RED);
            } else if (postalCode.getText().length() > 6) {
                warning.setText("Please make sure your postal code follows A0B1C0");
                warning.setVisible(true);
                postalCodeText.setTextFill(Color.RED);
            } else if (phoneNum.getText().length() > 15 || !phoneNum.getText().matches("\\(\\d{3}\\)\\d{3}-?\\d{4}")) {
                warning.setText("Please make sure your phone number follows (555)555-5555");
                warning.setVisible(true);
                phoneNumText.setTextFill(Color.RED);
            } else {

                customer.setFirstName(firstName.getText().trim());
                customer.setLastName(lastName.getText().trim());
                customer.setAddress(address.getText().trim());
                customer.setCity(city.getText().trim());
                customer.setPostalCode(postalCode.getText().trim());
                customer.setEmail(email.getText().trim());
                customer.setPhoneNumber(phoneNum.getText().trim());

                warning.setVisible(false);
                custTable.updateCustomer(customer);
                tableView.refresh();
            }

        });

        Button addVehicle = new Button("Add");
        Button updateVehicle = new Button("Update");
        Button add = new Button("Add");
        Button cancel = new Button("Cancel");
        Button delVehicle = new Button("Delete");
        delVehicle.setOnAction(e-> {
            Vehicles vehicle = vehicleListView.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("");
            alert.setGraphic(null);
            alert.setContentText("Are you sure you want to delete this vehicle?");
            if(!vehicleListView.getSelectionModel().isEmpty()) {
                if (alert.showAndWait().get() == ButtonType.OK) {
                    vehTable.deleteVehicle(vehicle);
                    customerVehicles = custVehTable.getVehicleCustomers(customer.getId());
                    vehicles.clear();
                    customerVehicles.forEach(n -> {
                        vehicles.add(vehTable.getVehicle(n.getVehicleid()));
                    });
                    vehicleListView.setItems(FXCollections.observableArrayList(vehicles));
                }
            }
        });

        //Vehicle list view title
        Text vehListViewText = new Text("Vehicle Information");
        vehListViewText.setFont(Font.font("Times New Roman", 20));

        updateCustomerBox.getChildren().addAll(customerInfo, firstNameText, firstName, lastNameText, lastName, addressText, address, cityText, city, postalCodeText, postalCode, emailText, email, phoneNumText, phoneNum, updateCust);
        updateCustomerBox.setSpacing(5);
        //updateCustomerBox.setBorder(new Border());

        HBox addVehicleButtons = new HBox();
        addVehicleButtons.getChildren().addAll(add, cancel);
        addVehicleButtons.setAlignment(Pos.CENTER);
        addVehicleButtons.setSpacing(10);
        addVehicleBox.getChildren().addAll(vehicleInfo, vinNumText, vinNum, brandText, comboBrand, modelText, comboModel, yearText, year, kilometersText, kilometers, addVehicleButtons);
        addVehicleBox.setSpacing(10);
        addVehicleBox.setVisible(false);

        HBox vehicleListButtons = new HBox(addVehicle, updateVehicle, delVehicle);
        vehicleListButtons.setSpacing(10);
        vehicleListButtons.setAlignment(Pos.CENTER);
        vehicleList.getChildren().addAll(vehListViewText, vehicleListView, vehicleListButtons);

        hBox.getChildren().addAll(updateCustomerBox, vehicleList, addVehicleBox);
        //hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(25);
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setVisible(false);

        addVehicle.setOnAction(e-> {
            vehicleInfo.setText("Add New Vehicle");
            vinNumText.setTextFill(Color.BLACK);
            yearText.setTextFill(Color.BLACK);
            brandText.setTextFill(Color.BLACK);
            modelText.setTextFill(Color.BLACK);
            kilometersText.setTextFill(Color.BLACK);
            vinNum.setText("");
            comboBrand.setValue("");
            comboModel.setValue("");
            year.setText("");
            kilometers.setText("");
            addVehicleBox.setVisible(true);
            warning.setVisible(false);
            add.setText("Add");
        });
        cancel.setOnAction(e-> {
            addVehicleBox.setVisible(false);
            warning.setVisible(false);
        });

        add.setOnAction(e-> {
            if(vinNum.getText().isEmpty()) {
                warning.setText("VIN cannot be left empty");
                warning.setVisible(true);
                vinNumText.setTextFill(Color.RED);
            } else if (year.getText().isEmpty()) {
                warning.setText("Year cannot be left empty");
                warning.setVisible(true);
                yearText.setTextFill(Color.RED);
            } else if (kilometers.getText().isEmpty()) {
                warning.setText("Kilometers cannot be left empty");
                warning.setVisible(true);
                kilometersText.setTextFill(Color.RED);
            } else if (comboBrand.getSelectionModel().isEmpty()) {
                warning.setText("You must select a brand");
                warning.setVisible(true);
                brandText.setTextFill(Color.RED);
            } else if (comboModel.getSelectionModel().isEmpty()) {
                warning.setText("You must select a model");
                warning.setVisible(true);
                modelText.setTextFill(Color.RED);
            } else if (vinNum.getText().length() > 17) {
                warning.setText("VIN cannot be longer than 17 characters");
                warning.setVisible(true);
                vinNumText.setTextFill(Color.RED);
            } else if (year.getText().length() > 4 || !StringUtils.isStrictlyNumeric(year.getText())) {
                warning.setText("Year cannot be longer than 4 digits");
                warning.setVisible(true);
                yearText.setTextFill(Color.RED);
            } else if (!StringUtils.isStrictlyNumeric(kilometers.getText()) || kilometers.getText().length() > 7) {
                warning.setText("Kilometers must be numeric");
                warning.setVisible(true);
                kilometersText.setTextFill(Color.RED);
            } else {
                if(add.getText().equals("Add")) {
                    vehTable.createVehicle(new Vehicles(vinNum.getText().trim(), comboBrand.getValue(), comboModel.getValue(), year.getText().trim(), kilometers.getText().trim()));
                    ArrayList<Vehicles> vehArray = vehTable.getAllVehicles();
                    custVehTable.createCustomerVehicle(new CustomerVehicles(customer.getId(), vehArray.get(vehArray.size() - 1).getId()));

                    vehicles.add(vehTable.getVehicle(vehArray.get(vehArray.size() - 1).getId()));
                } else {
                    vehicle.setVin(vinNum.getText().trim());
                    vehicle.setBrand(comboBrand.getValue());
                    vehicle.setModel(comboModel.getValue());
                    vehicle.setYear(year.getText().trim());
                    vehicle.setKilometers(kilometers.getText().trim());
                    vehTable.updateVehicle(vehicle);
                }

                vehicleListView.setItems(FXCollections.observableArrayList(vehicles));
                addVehicleBox.setVisible(false);
                warning.setVisible(false);
            }
        });

        updateVehicle.setOnAction(e-> {
            if(!vehicleListView.getSelectionModel().isEmpty()) {
                vehicle = vehicleListView.getSelectionModel().getSelectedItem();
                vinNum.setText(vehicle.getVin());
                comboBrand.setValue(vehicle.getBrand());
                comboModel.setValue(vehicle.getModel());
                year.setText(vehicle.getYear());
                kilometers.setText(vehicle.getKilometers());
                vinNumText.setTextFill(Color.BLACK);
                yearText.setTextFill(Color.BLACK);
                brandText.setTextFill(Color.BLACK);
                modelText.setTextFill(Color.BLACK);
                kilometersText.setTextFill(Color.BLACK);
                add.setText("Update");
                vehicleInfo.setText("Update Vehicle");
                addVehicleBox.setVisible(true);
                warning.setVisible(false);
            }
        });

        tableView.setRowFactory(tv -> {
            TableRow<Customers> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    customer = row.getItem();
                    customerVehicles = custVehTable.getVehicleCustomers(customer.getId());
                    vehicles.clear();
                    customerVehicles.forEach(e-> {
                        vehicles.add(vehTable.getVehicle(e.getVehicleid()));
                    });
                    firstName.setText(customer.getFirstName());
                    lastName.setText(customer.getLastName());
                    address.setText(customer.getAddress());
                    city.setText(customer.getCity());
                    postalCode.setText(customer.getPostalCode());
                    email.setText(customer.getEmail());
                    phoneNum.setText(customer.getPhoneNumber());
                    vehicleListView.setItems(FXCollections.observableArrayList(vehicles));
                    hBox.setVisible(true);
                    addVehicleBox.setVisible(false);
                    warning.setVisible(false);
                }
            });
            return row ;
        });

        hBox.setAlignment(Pos.CENTER);
        this.setTop(tableView);
        this.setCenter(warning);
        this.setBottom(hBox);
        this.setPadding(new Insets(10, 10, 10, 10));


    }
}