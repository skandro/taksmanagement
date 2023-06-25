module com.imconsulting {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires org.controlsfx.controls;
    requires java.sql;
    requires java.sql.rowset;

    opens com.imconsulting.customer to javafx.base, org.hibernate.orm.core;
    opens com.imconsulting.response to org.hibernate.orm.core;
    opens com.imconsulting.employee to org.hibernate.orm.core, javafx.base, jakarta.persistence;
    opens com.imconsulting.employee.privilege to org.hibernate.orm.core;
    opens com.imconsulting.profession to org.hibernate.orm.core;
    opens com.imconsulting.action to org.hibernate.orm.core, javafx.base;
    opens com.imconsulting.company to org.hibernate.orm.core, javafx.base;
    opens com.imconsulting.channel to org.hibernate.orm.core;
    opens com.imconsulting.empstatus to org.hibernate.orm.core;
    opens com.imconsulting.UI to org.hibernate.orm.core;
    exports com.imconsulting.UI;
    exports com.imconsulting.UI.paneli;
    opens com.imconsulting.UI.paneli to org.hibernate.orm.core;
    exports com.imconsulting;
    opens com.imconsulting to org.hibernate.orm.core;
    exports com.imconsulting.products;
    opens com.imconsulting.products to org.hibernate.orm.core;

}