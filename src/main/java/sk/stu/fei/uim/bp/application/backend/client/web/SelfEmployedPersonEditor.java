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
 * A Designer generated component for the self-employed-person-editor template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("self-employed-person-editor")
@JsModule("./views/client/self-employed-person-editor.js")
public class SelfEmployedPersonEditor extends PolymerTemplate<SelfEmployedPersonEditor.SelfEmployedPersonEditorModel> {

    @Id("firstName")
    private TextField firstName;
    @Id("surname")
    private TextField surname;
    @Id("email")
    private EmailField email;
    @Id("phone")
    private TextField phone;
    @Id("dateOfBirdth")
    private DatePicker dateOfBirdth;
    @Id("personalNumber")
    private TextField personalNumber;
    @Id("indentityCardNumber")
    private TextField indentityCardNumber;
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
    @Id("ico")
    private TextField ico;
    @Id("businessName")
    private TextField businessName;
    @Id("businessObject")
    private TextField businessObject;
    @Id("iban")
    private TextField iban;
    @Id("note")
    private TextArea note;
    @Id("cancel")
    private Button cancel;
    @Id("save")
    private Button save;

    /**
     * Creates a new SelfEmployedPersonEditor.
     */
    public SelfEmployedPersonEditor() {
        // You can initialise any data required for the connected UI components here.
    }

    /**
     * This model binds properties between SelfEmployedPersonEditor and self-employed-person-editor
     */
    public interface SelfEmployedPersonEditorModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
