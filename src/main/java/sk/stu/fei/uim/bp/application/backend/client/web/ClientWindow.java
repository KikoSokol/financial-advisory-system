package sk.stu.fei.uim.bp.application.backend.client.web;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

/**
 * A Designer generated component for the client-window template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("client-window")
@JsModule("./views/client/client-window.js")
public class ClientWindow extends PolymerTemplate<ClientWindow.ClientWindowModel> {

    @Id("informationTab")
    private Tab informationTab;
    @Id("chooseClient")
    private ComboBox<String> chooseClient;
    @Id("close")
    private Button close;

    /**
     * Creates a new ClientWindow.
     */
    public ClientWindow() {
        // You can initialise any data required for the connected UI components here.
    }

    /**
     * This model binds properties between ClientWindow and client-window
     */
    public interface ClientWindowModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
