package sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.Company;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.Product;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.ProductType;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.ProductTypeCategory;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.CompanyService;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.ProductService;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.ProductTypeService;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.controllers.ProductController;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.controllers.operations.ProductOperationController;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.CompanyDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.ProductDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.ProductTypeDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.editors.ProductEditor;
import sk.stu.fei.uim.bp.application.ui.NotificationProvider;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Tag("product-view")
@JsModule("./views/product/product-view.js")
public class ProductView extends PolymerTemplate<ProductView.ProductViewModel> {

    private ProductService productService;
    private ProductTypeService productTypeService;
    private CompanyService companyService;
    private final ObjectId currentAgent = new ObjectId("601b6300dbf3207494372a20");

    @Id("addProduct")
    private Button addProduct;

    @Id("productTable")
    private Grid<ProductDto> productTable;

    private Dialog window;

    private ProductEditor productEditor;

    private ProductController productController;

    private ProductOperationController productOperationController;


    public ProductView()
    {
        initColumns();


        initWindow();
        initProductEditor();

        setActionOnAddButton();

        this.productTable.addItemDoubleClickListener(item ->{
            ProductDto productDto = item.getItem();
            this.doUpdateProduct(productDto);
        });
    }

    public void initService(ProductService productService, ProductTypeService productTypeService, CompanyService companyService)
    {
        this.productService = productService;
        this.productTypeService = productTypeService;
        this.companyService = companyService;

        HeaderRow searchRow = this.productTable.appendHeaderRow();
        initSearchRow(searchRow);

        this.productController = new ProductController(productService, productTypeService,companyService,this,this.currentAgent);
        this.productOperationController = new ProductOperationController(productService, productTypeService,companyService, this,this.currentAgent);

    }

    private void initColumns()
    {
        Grid.Column<ProductDto> nameColumn = this.productTable.addColumn(ProductDto::getName);
        nameColumn.setAutoWidth(true);
        nameColumn.setHeader("Názov");
        nameColumn.setKey("nameColumn");
        nameColumn.setId("nameColumn");

        Grid.Column<ProductDto> productTypeColumn = this.productTable.addColumn(ProductDto::getProductTypeName);
        productTypeColumn.setAutoWidth(true);
        productTypeColumn.setHeader("Typ produktu");
        productTypeColumn.setKey("productTypeColumn");
        productTypeColumn.setId("productTypeColumn");

        Grid.Column<ProductDto> productTypeCategoryColumn = this.productTable.addColumn(ProductDto::getProductTypeCategoryName);
        productTypeCategoryColumn.setAutoWidth(true);
        productTypeCategoryColumn.setHeader("Kategória produktu");
        productTypeCategoryColumn.setKey("productTypeCategoryColumn");
        productTypeCategoryColumn.setId("productTypeCategoryColumn");

        Grid.Column<ProductDto> productCompanyColumn = this.productTable.addColumn(ProductDto::getCompanyName);
        productCompanyColumn.setAutoWidth(true);
        productCompanyColumn.setHeader("Spoločnosť");
        productCompanyColumn.setKey("productCompanyColumn");
        productCompanyColumn.setId("productCompanyColumn");

    }

    private void initSearchRow(HeaderRow searchRow)
    {
        initNameSearch(searchRow);
        initProductTypeSearch(searchRow);
        initProductTypeCategorySearch(searchRow);
        initProductCompanySearch(searchRow);
    }

    private void initNameSearch(HeaderRow headerRow)
    {
        Grid.Column<ProductDto> column = this.productTable.getColumnByKey("nameColumn");

        TextField nameSearch = new TextField();
        nameSearch.addValueChangeListener(event -> {
            this.productController.getProductFilter().setName(event.getValue());
            this.productController.filterDataInTable();
        });
        nameSearch.setValueChangeMode(ValueChangeMode.EAGER);


        headerRow.getCell(column).setComponent(nameSearch);
        nameSearch.setSizeFull();
        nameSearch.setPlaceholder("Filter");
    }

    private void initProductTypeSearch(HeaderRow headerRow)
    {
        Grid.Column<ProductDto> column = this.productTable.getColumnByKey("productTypeColumn");

        Select<ProductTypeDto> productTypeSearch = new Select<>();
        productTypeSearch.setEmptySelectionAllowed(true);
        productTypeSearch.setEmptySelectionCaption("");
        productTypeSearch.setItems(getListOfProductTypeDto());
        productTypeSearch.setTextRenderer(ProductTypeDto::getName);

        productTypeSearch.addValueChangeListener(event -> {
            this.productController.getProductFilter().setProductType(event.getValue());
            this.productController.filterDataInTable();
        });

        headerRow.getCell(column).setComponent(productTypeSearch);
        productTypeSearch.setSizeFull();
        productTypeSearch.setPlaceholder("Filter");
    }

    private void initProductTypeCategorySearch(HeaderRow headerRow)
    {
        Grid.Column<ProductDto> column = this.productTable.getColumnByKey("productTypeCategoryColumn");

        Select<ProductTypeCategory> productTypeCategorySearch = new Select<>();
        productTypeCategorySearch.setEmptySelectionAllowed(true);
        productTypeCategorySearch.setEmptySelectionCaption("");
        productTypeCategorySearch.setItems(ProductTypeCategory.values());
        productTypeCategorySearch.setTextRenderer(ProductTypeCategory::getName);

        productTypeCategorySearch.addValueChangeListener(event -> {
            this.productController.getProductFilter().setProductTypeCategory(event.getValue());
            this.productController.filterDataInTable();
        });

        headerRow.getCell(column).setComponent(productTypeCategorySearch);
        productTypeCategorySearch.setSizeFull();
        productTypeCategorySearch.setPlaceholder("Filter");
    }

    private void initProductCompanySearch(HeaderRow headerRow)
    {
        Grid.Column<ProductDto> column = this.productTable.getColumnByKey("productCompanyColumn");

        Select<CompanyDto> productCompanySearch = new Select<>();
        productCompanySearch.setEmptySelectionAllowed(true);
        productCompanySearch.setEmptySelectionCaption("");
        productCompanySearch.setItems(getListOfCompanyDto());
        productCompanySearch.setTextRenderer(CompanyDto::getName);

        productCompanySearch.addValueChangeListener(event -> {
            this.productController.getProductFilter().setCompany(event.getValue());
            this.productController.filterDataInTable();
        });

        headerRow.getCell(column).setComponent(productCompanySearch);
        productCompanySearch.setSizeFull();
        productCompanySearch.setPlaceholder("Filter");
    }

    private void initProductEditor()
    {
        this.productEditor = new ProductEditor();
    }

    private void initWindow()
    {
        this.window = new Dialog();
        this.window.setModal(true);
        this.window.setCloseOnOutsideClick(false);
    }

    private void setActionOnAddButton()
    {
        this.addProduct.addClickListener(buttonClickEvent -> this.productOperationController.addNewProduct());
    }

    public void showWindow(Component component)
    {
        this.window.add(component);
        this.window.open();
    }

    public void closeWindow()
    {
        this.window.removeAll();
        this.window.close();
    }

    public void refreshTable()
    {
        this.productController.refreshTableData();
    }

    private void doUpdateProduct(ProductDto productDto)
    {
        Optional<Product> selectedProduct = this.productController.getProductById(productDto.getProductId());

        if(selectedProduct.isPresent())
        {
            Product product = selectedProduct.get();
            this.productOperationController.updateProduct(product);
        }
    }

    public void showSuccessOperationNotification(String successMessage)
    {
        NotificationProvider notificationProvider = new NotificationProvider();
        notificationProvider.showSuccessMessage(successMessage,5000);
    }

    public void showErrorMessage(String errorMessage)
    {
        NotificationProvider notificationProvider = new NotificationProvider();
        notificationProvider.showErrorMessage(errorMessage);
    }



    public List<ProductTypeDto> getListOfProductTypeDto()
    {
        List<ProductType> productTypes = this.productTypeService.getAllProductTypeOfCurrentAgent(this.currentAgent);

        List<ProductTypeDto> productDtos = new LinkedList<>();

        for(ProductType productType : productTypes)
        {
            productDtos.add(new ProductTypeDto(productType));
        }

        return productDtos;
    }

    public List<CompanyDto> getListOfCompanyDto()
    {
        List<Company> companies = this.companyService.getAllCompanyByCurrentAgent(this.currentAgent);

        List<CompanyDto> companyDtos = new LinkedList<>();

        for(Company company : companies)
        {
            companyDtos.add(new CompanyDto(company));
        }

        return companyDtos;
    }

    public Grid<ProductDto> getProductTable() {
        return this.productTable;
    }

    public ProductEditor getProductEditor() {
        return this.productEditor;
    }

    public interface ProductViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
