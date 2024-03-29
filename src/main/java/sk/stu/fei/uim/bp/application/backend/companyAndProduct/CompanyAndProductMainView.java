package sk.stu.fei.uim.bp.application.backend.companyAndProduct;

import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import sk.stu.fei.uim.bp.application.MainView;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.CompanyService;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.ProductService;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.ProductTypeService;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.implementation.CompanyServiceIml;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.implementation.ProductServiceImpl;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.implementation.ProductTypeServiceImpl;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.events.companyViewEvents.CompanyViewEvent;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.views.CompanyView;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.views.ProductTypeView;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.views.ProductView;


@Tag("company-and-product-main-view")
@JsModule("./views/companyAndProductView/company-and-product-main-view.js")
@PageTitle(value = "Spoločnosti a produkty")
@Route(value = "spolocnosti", layout = MainView.class)
public class CompanyAndProductMainView extends PolymerTemplate<CompanyAndProductMainView.CompanyAndProductMainViewModel> {


    @Id("board")
    private Board board;

    @Id("companyView")
    private CompanyView companyView;

    @Id("productView")
    private ProductView productView;

    @Id("productTypeView")
    private ProductTypeView productTypeView;


    private final ProductTypeService productTypeService;
    private final CompanyService companyService;
    private final ProductService productService;

    @Autowired
    public CompanyAndProductMainView(ProductTypeServiceImpl productTypeService, CompanyServiceIml companyService, ProductServiceImpl productService)
    {
        this.productTypeService = productTypeService;
        this.productTypeView.initService(this.productTypeService);

        this.companyService = companyService;
        this.companyView.initService(this.companyService);

        this.productService = productService;
        this.productView.initService(productService,productTypeService,companyService);

        this.companyView.addListener(CompanyViewEvent.class,this::updateProductsView);
    }

    private void updateProductsView(CompanyViewEvent event)
    {
       this.productView.refreshTable();
    }








    public interface CompanyAndProductMainViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
