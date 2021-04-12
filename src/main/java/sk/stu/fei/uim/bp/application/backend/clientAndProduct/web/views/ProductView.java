package sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

/**
 * A Designer generated component for the product-view template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("product-view")
@JsModule("./views/product/product-view.js")
public class ProductView extends PolymerTemplate<ProductView.ProductViewModel> {

    @Id("addProduct")
    private Button addProduct;

    @Id("productTable")
    private Grid productTable;

    /**
     * Creates a new ProductView.
     */
    public ProductView() {
        // You can initialise any data required for the connected UI components here.
    }

    /**
     * This model binds properties between ProductView and product-view
     */
    public interface ProductViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
