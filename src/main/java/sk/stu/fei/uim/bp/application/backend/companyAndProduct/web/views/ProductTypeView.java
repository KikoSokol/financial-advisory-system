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

import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.ProductType;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.ProductTypeCategory;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.ProductTypeService;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.controllers.ProductTypeController;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.controllers.operations.ProductTypeOperationController;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.ProductTypeDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.editors.ProductTypeEditor;
import sk.stu.fei.uim.bp.application.ui.NotificationProvider;

import java.util.Optional;


@Tag("product-type-view")
@JsModule("./views/product/product-type-view.js")
public class ProductTypeView extends PolymerTemplate<ProductTypeView.ProductTypeViewModel> {

    private ProductTypeService productTypeService;
    private final ObjectId currentAgent = new ObjectId("601b6300dbf3207494372a20");

    @Id("addProductType")
    private Button addProductType;

    @Id("productTypeTable")
    private Grid<ProductTypeDto> productTypeTable;

    private Dialog window;

    private ProductTypeEditor productTypeEditor;

    private ProductTypeController productTypeController;

    private ProductTypeOperationController productTypeOperationController;

    public ProductTypeView()
    {

        initColumns();
        HeaderRow searchRow = this.productTypeTable.appendHeaderRow();
        initSearchRow(searchRow);

        initWindow();
        initProductTypeEditor();

        setActionOnAddButton();

        this.productTypeTable.addItemDoubleClickListener(item ->{
            ProductTypeDto productTypeDto = item.getItem();
            this.doUpdateProductType(productTypeDto);
        });
    }

    public void initService(ProductTypeService productTypeService)
    {
        this.productTypeService = productTypeService;
        this.productTypeController = new ProductTypeController(this.productTypeService,this,this.currentAgent);
        this.productTypeOperationController = new ProductTypeOperationController(this.productTypeService,this,this.currentAgent);
    }

    private void initColumns()
    {
        Grid.Column<ProductTypeDto> nameColumn = this.productTypeTable.addColumn(ProductTypeDto::getName);
        nameColumn.setAutoWidth(true);
        nameColumn.setHeader("Názov");
        nameColumn.setKey("nameColumn");
        nameColumn.setId("nameColumn");

        Grid.Column<ProductTypeDto> categoryColumn = this.productTypeTable.addColumn(ProductTypeDto::getCategoryName);
        categoryColumn.setAutoWidth(true);
        categoryColumn.setHeader("Kategória");
        categoryColumn.setKey("categoryColumn");
        categoryColumn.setId("categoryColumn");

    }

    private void initSearchRow(HeaderRow searchRow)
    {
        initNameSearch(searchRow);
        initCategorySearch(searchRow);
    }

    private void initNameSearch(HeaderRow headerRow)
    {
        Grid.Column<ProductTypeDto> column = this.productTypeTable.getColumnByKey("nameColumn");

        TextField nameSearch = new TextField();
        nameSearch.addValueChangeListener(event -> {
            this.productTypeController.getProductTypeFilter().setName(event.getValue());
            this.productTypeController.filterDataInTable();
        });
        nameSearch.setValueChangeMode(ValueChangeMode.EAGER);


        headerRow.getCell(column).setComponent(nameSearch);
        nameSearch.setSizeFull();
        nameSearch.setPlaceholder("Filter");
    }

    private void initCategorySearch(HeaderRow headerRow)
    {
        Grid.Column<ProductTypeDto> column = this.productTypeTable.getColumnByKey("categoryColumn");

        Select<ProductTypeCategory> categorySearch = new Select<>();
        categorySearch.setEmptySelectionAllowed(true);
        categorySearch.setEmptySelectionCaption("");
        categorySearch.setItems(ProductTypeCategory.values());
        categorySearch.setTextRenderer(ProductTypeCategory::getName);

        categorySearch.addValueChangeListener(event -> {
           this.productTypeController.getProductTypeFilter().setProductTypeCategory(event.getValue());
           this.productTypeController.filterDataInTable();
        });

        headerRow.getCell(column).setComponent(categorySearch);
        categorySearch.setSizeFull();
        categorySearch.setPlaceholder("Filter");
    }

    private void initProductTypeEditor()
    {
        this.productTypeEditor = new ProductTypeEditor();
    }

    private void initWindow()
    {
        this.window = new Dialog();
        this.window.setModal(true);
        this.window.setCloseOnOutsideClick(false);
    }


    private void setActionOnAddButton()
    {
        this.addProductType.addClickListener(event -> this.productTypeOperationController.addNewProductType());
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
        this.productTypeController.refreshTableData();
    }

    private void doUpdateProductType(ProductTypeDto productTypeDto)
    {
        Optional<ProductType> selectedProductType = this.productTypeController.getProductTypeById(productTypeDto.getProductTypeId());

        if(selectedProductType.isEmpty())
        {
            showErrorMessage("Typ produktu sa nepodarilo načítať");
        }
        else
        {
            ProductType productType = selectedProductType.get();
            this.productTypeOperationController.updateProductType(productType);
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


    public Grid<ProductTypeDto> getProductTypeTable()
    {
        return this.productTypeTable;
    }

    public ProductTypeEditor getProductTypeEditor()
    {
        return this.productTypeEditor;
    }






















    public interface ProductTypeViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
