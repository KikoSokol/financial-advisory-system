package sk.stu.fei.uim.bp.application.backend.client.web;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

/**
 * A Designer generated component for the physical-person-editor template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("physical-person-editor")
@JsModule("./views/client/physical-person-editor.js")
public class PhysicalPersonEditor extends PolymerTemplate<PhysicalPersonEditor.PhysicalPersonEditorModel> {

    @Id("firstName")
    private TextField firstName;
    @Id("surname")
    private TextField surname;
    @Id("phone")
    private TextField phone;
    @Id("email")
    private EmailField email;
    @Id("dateOfBirth")
    private DatePicker dateOfBirth;
    @Id("personalNumber")
    private TextField personalNumber;
    @Id("identityCardNumber")
    private TextField identityCardNumber;
    @Id("citizenship")
    private TextField citizenship;
    @Id("dateOfValidityOfIdentityCard")
    private DatePicker dateOfValidityOfIdentityCard;
    @Id("releaseDateOfIdentityCard")
    private DatePicker releaseDateOfIdentityCard;
    @Id("street")
    private TextField street;
    @Id("numberOfHouse")
    private TextField numberOfHouse;
    @Id("city")
    private TextField city;
    @Id("postalCode")
    private TextField postalCode;
    @Id("state")
    private TextField state;
    @Id("identityCardCopy")
    private PersonalCardComponent identityCardCopy;
    @Id("iban")
    private TextField iban;
    @Id("note")
    private TextArea note;
    @Id("cancel")
    private Button cancel;
    @Id("save")
    private Button save;

    /**
     * Creates a new PhysicalPersonEditor.
     */
    public PhysicalPersonEditor() {
        // You can initialise any data required for the connected UI components here.
    }

    /**
     * This model binds properties between PhysicalPersonEditor and physical-person-editor
     */
    public interface PhysicalPersonEditorModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
