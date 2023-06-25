package com.imconsulting.action;

import com.imconsulting.UI.Controller;
import com.imconsulting.channel.Channel;
import com.imconsulting.customer.Customer;
import com.imconsulting.customer.CustomerPanel;
import com.imconsulting.products.Products;
import com.imconsulting.response.Response;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.function.Consumer;

public class ActionEmployeePanel extends VBox {
    private final Label currentEmployeeLabel = new Label();
    private final Button backButton = new Button("Back");
    private TableView<Action> actionTableView = new TableView<>();
    private ActionController actionController = new ActionController();


    public ActionEmployeePanel() {
        setSpacing(10);
        setPadding(new Insets(10));

        currentEmployeeLabel.setText(Controller.getCurrentEmployee().getName() + ", " + Controller.getCurrentEmployee().getSurname());
        BorderPane backButtonAndEmployeePanel = new BorderPane(null, null, currentEmployeeLabel, null, backButton);
        backButton.setOnAction(this::onClickBackButton);

        setupTableView();

        getChildren().addAll(backButtonAndEmployeePanel, actionTableView);
    }

    private void setupTableView() {
        ObservableList<Action> actionObservableList = actionController.loadActionsByEmployee();
        actionTableView.setItems(actionObservableList);
        actionTableView.setEditable(true);

        TableColumn<Action, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Action, LocalDate> dateColumn = new TableColumn<>("Datum");
        dateColumn.setMinWidth(150);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Action, Customer> customerColumn = new TableColumn<>("Kupac");
        customerColumn.setMinWidth(150);
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));

        TableColumn<Action, Channel> channelColumn = new TableColumn<>("Kanal");
        channelColumn.setMinWidth(150);
        channelColumn.setCellValueFactory(new PropertyValueFactory<>("channel"));
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Controller.PU_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createNamedQuery("Channel.findAll");
        Channel[] channelsArray = (Channel[]) query.getResultList().toArray(new Channel[0]);
        entityManager.getTransaction().commit();
        channelColumn.setCellFactory(ComboBoxTableCell.forTableColumn(channelsArray));
        channelColumn.setOnEditCommit(event -> onChannelFiledChange(event, r -> r.setChannel(event.getNewValue())));

        TableColumn<Action, Response> responseColumn = new TableColumn<>("Odgovor");
        responseColumn.setMinWidth(150);
        responseColumn.setCellValueFactory(new PropertyValueFactory<>("response"));
        entityManager.getTransaction().begin();
        Query query1 = entityManager.createNamedQuery("Response.findAll");
        Response[] responsesArray = (Response[]) query1.getResultList().toArray(new Response[0]);
        entityManager.getTransaction().commit();
        responseColumn.setCellFactory(ComboBoxTableCell.forTableColumn(responsesArray));
        responseColumn.setOnEditCommit(event -> onResponseFiledChange(event, r -> r.setResponse(event.getNewValue())));

        TableColumn<Action, Products> productsColumn = new TableColumn<>("Proizvod");
        productsColumn.setMinWidth(150);
        productsColumn.setCellValueFactory(new PropertyValueFactory<>("products"));
        entityManager.getTransaction().begin();
        Query query2 = entityManager.createNamedQuery("Products.findAll");
        Products[] productsArray = (Products[]) query2.getResultList().toArray(new Products[0]);
        entityManager.getTransaction().commit();
        productsColumn.setCellFactory(ComboBoxTableCell.forTableColumn(productsArray));
        productsColumn.setOnEditCommit(event -> onResponseFiledChange(event, r -> r.setProducts(event.getNewValue())));

        TableColumn<Action, String> descriptionColumn = new TableColumn<>("Opis");
        descriptionColumn.setMinWidth(150);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setOnEditCommit(event -> onDescriptionFiledChange(event, r -> r.setDescription(event.getNewValue())));


        actionTableView.getColumns().addAll(idColumn, dateColumn, customerColumn, channelColumn, responseColumn, productsColumn, descriptionColumn);

    }

    private <F> void onProductsFiledChange(TableColumn.CellEditEvent<Action, F> event, Consumer<Action> actionConsumer) {
        Action editAction = event.getRowValue();
        actionConsumer.accept(editAction);
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Controller.PU_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(editAction);
        entityManager.getTransaction().commit();
    }

    private <F> void onChannelFiledChange(TableColumn.CellEditEvent<Action, F> event, Consumer<Action> actionConsumer) {
        Action editAction = event.getRowValue();
        actionConsumer.accept(editAction);
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Controller.PU_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(editAction);
        entityManager.getTransaction().commit();
    }

    private <F> void onResponseFiledChange(TableColumn.CellEditEvent<Action, F> event, Consumer<Action> actionConsumer) {
        Action editAction = event.getRowValue();
        actionConsumer.accept(editAction);
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Controller.PU_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(editAction);
        entityManager.getTransaction().commit();
    }

    private <F> void onDescriptionFiledChange(TableColumn.CellEditEvent<Action, F> event, Consumer<Action> actionConsumer) {
        Action editAction = event.getRowValue();
        actionConsumer.accept(editAction);
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Controller.PU_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(editAction);
        entityManager.getTransaction().commit();
    }


    private void onClickBackButton(ActionEvent actionEvent) {
        CustomerPanel customerPanel = new CustomerPanel();
        Scene scene = new Scene(customerPanel);
        Controller.instance().getMainStage().setScene(scene);
        Controller.instance().getMainStage().setTitle("Customers");
    }
}
