package sk.stu.fei.uim.bp.application.backend.client.web;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

/**
 * A Designer generated component for the company-editor template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("company-editor")
@JsModule("./views/client/company-editor.js")
public class CompanyEditor extends PolymerTemplate<CompanyEditor.CompanyEditorModel> {

    @Id("businessName")
    private TextField businessName;
    @Id("ico")
    private TextField ico;
    @Id("dic")
    private TextField dic;
    @Id("dicDph")
    private TextField dicDph;
    @Id("businessObject")
    private TextField businessObject;
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
    @Id("addManager")
    private Button addManager;
    @Id("managerTable")
    private Grid managerTable;
    @Id("searchedManagerTable")
    private Grid searchedManagerTable;
    @Id("cancel")
    private Button cancel;
    @Id("save")
    private Button save;
    @Id("seachManager")
    private SearchClientView seachManager;

    /**
     * Creates a new CompanyEditor.
     */
    public CompanyEditor() {
        // You can initialise any data required for the connected UI components here.
    }

    /**
     * This model binds properties between CompanyEditor and company-editor
     */
    public interface CompanyEditorModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
