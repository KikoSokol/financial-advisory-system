package sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import org.bson.types.ObjectId;

import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.Company;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.CompanyService;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.controllers.CompanyController;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.controllers.operations.CompanyOperationController;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.CompanyDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.editors.CompanyEditor;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.events.companyViewEvents.CompanyViewEvent;
import sk.stu.fei.uim.bp.application.ui.NotificationProvider;

import java.util.Optional;


@Tag("company-view")
@JsModule("./views/company/company-view.js")
public class CompanyView extends PolymerTemplate<CompanyView.CompanyViewModel> {

    private CompanyService companyService;
    private final ObjectId currentAgent = new ObjectId("601b6300dbf3207494372a20");


    @Id("addCompany")
    private Button addCompany;

    @Id("companyTable")
    private Grid<CompanyDto> companyTable;

    private Dialog window;

    private CompanyEditor companyEditor;

    private CompanyController companyController;

    private CompanyOperationController companyOperationController;


    public CompanyView()
    {
        initColumns();
        HeaderRow searchRow = this.companyTable.appendHeaderRow();
        initSearchRow(searchRow);

        initWindow();
        initCompanyEditor();

        setActionOnAddButton();

        this.getCompanyTable().addItemDoubleClickListener(item -> {
            CompanyDto companyDto = item.getItem();
            this.doUpdateCompany(companyDto);
        });
    }

    public void initService(CompanyService companyService)
    {
        this.companyService = companyService;
        this.companyController = new CompanyController(this.companyService,this,this.currentAgent);
        this.companyOperationController = new CompanyOperationController(this.companyService,this,this.currentAgent);
    }

    private void initColumns()
    {
        Grid.Column<CompanyDto> nameColumn = this.companyTable.addColumn(CompanyDto::getName);
        nameColumn.setAutoWidth(true);
        nameColumn.setHeader("Názov");
        nameColumn.setKey("nameColumn");
        nameColumn.setId("nameColumn");

        Grid.Column<CompanyDto> icoColumn = this.companyTable.addColumn(CompanyDto::getIco);
        icoColumn.setAutoWidth(true);
        icoColumn.setHeader("IČO");
        icoColumn.setKey("icoColumn");
        icoColumn.setId("icoColumn");

        Grid.Column<CompanyDto> addressColumn = this.companyTable.addColumn(CompanyDto::getFullAddress);
        addressColumn.setAutoWidth(true);
        addressColumn.setHeader("Adresa");
        addressColumn.setKey("addressColumn");
        addressColumn.setId("addressColumn");
    }

    private void initSearchRow(HeaderRow headerRow)
    {
        initNameSearch(headerRow);
        initIcoSearch(headerRow);
        initAddressSearch(headerRow);
    }

    private void initNameSearch(HeaderRow headerRow)
    {
        Grid.Column<CompanyDto> column = this.companyTable.getColumnByKey("nameColumn");

        TextField nameSearch = new TextField();
        nameSearch.addValueChangeListener(event -> {
            this.companyController.getCompanyFilter().setName(event.getValue());
            this.companyController.filterDataInTable();
        });
        nameSearch.setValueChangeMode(ValueChangeMode.EAGER);


        headerRow.getCell(column).setComponent(nameSearch);
        nameSearch.setSizeFull();
        nameSearch.setPlaceholder("Filter");
    }

    private void initIcoSearch(HeaderRow headerRow)
    {
        Grid.Column<CompanyDto> column = this.companyTable.getColumnByKey("icoColumn");

        TextField icoSearch = new TextField();
        icoSearch.addValueChangeListener(event -> {
            this.companyController.getCompanyFilter().setIco(event.getValue());
            this.companyController.filterDataInTable();
        });
        icoSearch.setValueChangeMode(ValueChangeMode.EAGER);

        headerRow.getCell(column).setComponent(icoSearch);
        icoSearch.setSizeFull();
        icoSearch.setPlaceholder("Filter");
    }

    private void initAddressSearch(HeaderRow headerRow)
    {
        Grid.Column<CompanyDto> column = this.companyTable.getColumnByKey("addressColumn");

        TextField addressSearch = new TextField();
        addressSearch.addValueChangeListener(event -> {
            this.companyController.getCompanyFilter().setAddress(event.getValue());
            this.companyController.filterDataInTable();
        });
        addressSearch.setValueChangeMode(ValueChangeMode.EAGER);

        headerRow.getCell(column).setComponent(addressSearch);
        addressSearch.setSizeFull();
        addressSearch.setPlaceholder("Filter");
    }

    private void initCompanyEditor()
    {
        this.companyEditor = new CompanyEditor();
    }

    private void initWindow()
    {
        this.window = new Dialog();
        this.window.setModal(true);
        this.window.setCloseOnOutsideClick(false);
    }

    private void setActionOnAddButton()
    {
        this.addCompany.addClickListener(event -> this.companyOperationController.addNewCompany());
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
        this.companyController.refreshTableData();
    }

    private void doUpdateCompany(CompanyDto companyDto)
    {
        Optional<Company> selectedCompany = this.companyController.getCompanyById(companyDto.getCompanyId());

        if(selectedCompany.isPresent())
        {
            Company company = selectedCompany.get();
            this.companyOperationController.updateCompany(company);
        }
        else
        {
            showErrorMessage("Spoločnosť sa nepodarilo načítať");
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


    public Grid<CompanyDto> getCompanyTable() {
        return this.companyTable;
    }

    public CompanyEditor getCompanyEditor() {
        return this.companyEditor;
    }

    public void fireEventOnThisView()
    {
        fireEvent(new CompanyViewEvent(this));
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener)
    {
        return getEventBus().addListener(eventType,listener);
    }


    public interface CompanyViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
