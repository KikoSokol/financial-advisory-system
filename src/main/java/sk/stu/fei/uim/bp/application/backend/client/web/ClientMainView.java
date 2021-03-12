package sk.stu.fei.uim.bp.application.backend.client.web;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import sk.stu.fei.uim.bp.application.MainView;

/**
 * A Designer generated component for the client-main-view template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("client-main-view")
@JsModule("./views/client/client-main-view.js")
@PageTitle(value = "Klienti")
@Route(value = "klienti", layout = MainView.class)
public class ClientMainView extends PolymerTemplate<ClientMainView.ClientMainViewModel> {

    @Id("addPhysicalPersonButton")
    private Button addPhysicalPersonButton;
    @Id("addSelfEmployedPersonButton")
    private Button addSelfEmployedPersonButton;
    @Id("addCompanyButton")
    private Button addCompanyButton;
    @Id("clientTable")
    private Grid clientTable;


    /**
     * Creates a new ClientMainView.
     */
    public ClientMainView() {
        // You can initialise any data required for the connected UI components here.

    }

    /**
     * This model binds properties between ClientMainView and client-main-view
     */
    public interface ClientMainViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
