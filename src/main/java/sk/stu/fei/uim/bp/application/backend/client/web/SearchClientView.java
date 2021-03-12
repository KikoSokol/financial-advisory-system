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
 * A Designer generated component for the search-client-view template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("search-client-view")
@JsModule("./views/client/search-client-view.js")
public class SearchClientView extends PolymerTemplate<SearchClientView.SearchClientViewModel> {

    @Id("search")
    private TextField search;
    @Id("tableClient")
    private Grid tableClient;
    @Id("cancel")
    private Button cancel;
    @Id("add")
    private Button add;

    /**
     * Creates a new SearchClientView.
     */
    public SearchClientView() {
        // You can initialise any data required for the connected UI components here.
    }

    /**
     * This model binds properties between SearchClientView and search-client-view
     */
    public interface SearchClientViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
