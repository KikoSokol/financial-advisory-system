package sk.stu.fei.uim.bp.application.backend.contracts.web.editors;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import sk.stu.fei.uim.bp.application.backend.client.web.components.PhysicalPersonCard;
import sk.stu.fei.uim.bp.application.backend.file.components.FileAttachmentsAddView;
import sk.stu.fei.uim.bp.application.backend.file.components.FileAttachmentsViewer;

/**
 * A Designer generated component for the life-insurance-editor template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("life-insurance-editor")
@JsModule("./views/contracts/editors/life-insurance-editor.js")
public class LifeInsuranceEditor extends PolymerTemplate<LifeInsuranceEditor.LifeInsuranceEditorModel> {

    @Id("contractNumber")
    private TextField contractNumber;
    @Id("product")
    private ComboBox<String> product;
    @Id("dateOfStart")
    private DatePicker dateOfStart;
    @Id("payment")
    private BigDecimalField payment;
    @Id("anniversaryDate")
    private DatePicker anniversaryDate;
    @Id("paymentFrequency")
    private Select paymentFrequency;
    @Id("dateOfEnd")
    private DatePicker dateOfEnd;
    @Id("note")
    private TextArea note;
    @Id("addOwner")
    private Button addOwner;
    @Id("ownerInfo")
    private PhysicalPersonCard ownerInfo;
    @Id("addInsured")
    private Button addInsured;
    @Id("equalsClients")
    private Button equalsClients;
    @Id("insuredInfo")
    private PhysicalPersonCard insuredInfo;
    @Id("cancel")
    private Button cancel;
    @Id("save")
    private Button save;
    @Id("attachmentsView")
    private FileAttachmentsViewer attachmentsView;
    @Id("attachmentsAdd")
    private FileAttachmentsAddView attachmentsAdd;

    /**
     * Creates a new LifeInsuranceEditor.
     */
    public LifeInsuranceEditor() {
        // You can initialise any data required for the connected UI components here.
    }

    /**
     * This model binds properties between LifeInsuranceEditor and life-insurance-editor
     */
    public interface LifeInsuranceEditorModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
