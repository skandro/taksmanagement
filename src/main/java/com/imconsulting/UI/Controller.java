package com.imconsulting.UI;

import com.imconsulting.company.Company;
import com.imconsulting.customer.Customer;
import com.imconsulting.employee.Employee;
import javafx.stage.Stage;

public class Controller {
    public static String PU_NAME = "projectPU";
    private static Controller INSTANCE = null; //da bi bio singleton

    private Stage mainStage; //stage iz pocetne klase, da bi bio vidljiv u svim klasama

    private static Employee currentEmployee; //trenutno logovani employee
    private static Employee editEmployee;
    private static Customer editCustomer;
    private static Customer selectedCustomer;
    private static Company currentCompany;
    private static Company editCompany;
    private Stage editStage = new Stage();

    /*
    Objekt koji pripada klasi naziva se instance te klase.
    Varijable koje taj objekt sadrži nazivaju se varijable instance.
    Potprogrami koje objekt sadrži nazivaju se metode instance.

    Singleton je klasa koja omogućava stvaranje samo jedne instance sebe i daje pristup toj stvorenoj instanci.
    Sadrži statičke varijable koje mogu ugostiti jedinstvene i privatne instance sebe.
    Koristi se u scenarijima kada korisnik želi ograničiti primjerak klase na samo jedan objekt.
    To je korisno obično kada je za koordiniranje akcija kroz sustav potreban jedan objekt.

    Singleton uzorak koristi se u programskim jezicima kao što su Java i .NET za definiranje globalne varijable.
    Jedan objekt koji se koristi u svim sustavima ostaje stalan i treba ga definirati samo jednom, a ne mnogo puta.

     */


    private Controller() {

    }

    public static Company getCurrentCompany() {
        return currentCompany;
    }

    public static void setCurrentCompany(Company currentCompany) {
        Controller.currentCompany = currentCompany;
    }

    public static Company getEditCompany() {
        return editCompany;
    }

    public static void setEditCompany(Company editCompany) {
        Controller.editCompany = editCompany;
    }

    public Stage getEditStage() {
        return editStage;
    }

    public void setEditStage(Stage editStage) {
        this.editStage = editStage;
    }

    public static Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public static void setSelectedCustomer(Customer selectedCustomer) {
        Controller.selectedCustomer = selectedCustomer;
    }

    public static Customer getEditCustomer() {
        return editCustomer;
    }

    public static void setEditCustomer(Customer editCustomer) {
        Controller.editCustomer = editCustomer;
    }

    public static Employee getEditEmployee() {
        return editEmployee;
    }

    public static void setEditEmployee(Employee editEmployee) {
        Controller.editEmployee = editEmployee;
    }

    public static Employee getCurrentEmployee() {
        return currentEmployee;
    }

    public static void setCurrentEmployee(Employee currentEmployee) {
        Controller.currentEmployee = currentEmployee;
    }

    public Stage getMainStage() {
        mainStage.centerOnScreen();
        return mainStage;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    } //setuje stage

    public static Controller instance() {
        if (INSTANCE == null) {
            INSTANCE = new Controller(); //samo se prvi put konstruise
        }
        return INSTANCE;
    }
}
