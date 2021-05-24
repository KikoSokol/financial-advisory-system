package sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.controllers.operations;

import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.Company;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.CompanyService;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.CompanyDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.editors.CompanyEditor;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.events.companyEvents.CompanyCancelEvent;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.events.companyEvents.CompanyDeleteEvent;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.events.companyEvents.CompanySaveEvent;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.events.companyEvents.CompanyUpdateEvent;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.events.companyViewEvents.CompanyViewEvent;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.views.CompanyView;


public class CompanyOperationController {

    private final CompanyService companyService;

    private final CompanyView companyView;
    private final ObjectId currentAgentId;

    private boolean isNew;
    private Company company;
    private CompanyDto companyDto;


    public CompanyOperationController(CompanyService companyService, CompanyView companyView, ObjectId currentAgentId)
    {
        this.companyService = companyService;
        this.companyView = companyView;
        this.currentAgentId = currentAgentId;
        this.initActionOfEditor();
        this.clear();

    }

    private void initActionOfEditor()
    {
        CompanyEditor companyEditor = this.companyView.getCompanyEditor();

        companyEditor.addListener(CompanySaveEvent.class,this::doSaveNewCompany);
        companyEditor.addListener(CompanyUpdateEvent.class,this::doUpdateCompany);
        companyEditor.addListener(CompanyCancelEvent.class,this::cancelEdit);
        companyEditor.addListener(CompanyDeleteEvent.class,this::doDeleteCompany);
    }

    private void openEditor(CompanyDto companyDto, boolean isNew)
    {
        CompanyEditor editor = this.companyView.getCompanyEditor();
        editor.setCompanyDto(companyDto,isNew);
        this.companyView.showWindow(editor);
    }



    public void addNewCompany()
    {
        this.isNew = true;

        this.company = new Company();
        this.company.setAgent(this.currentAgentId);
        this.companyDto = new CompanyDto(this.company);

        openEditor(this.companyDto,this.isNew);

    }

    public void updateCompany(Company company)
    {
        this.isNew = false;
        this.company = company;
        this.companyDto = new CompanyDto(this.company);

        openEditor(this.companyDto,this.isNew);
    }

    private void doSaveNewCompany(CompanySaveEvent event)
    {
        this.companyDto = event.getCompanyDto();

        try
        {
            this.company = this.companyDto.toCompany(this.company);
            this.companyService.addNewCompany(this.company);
            successOperation("Nová spoločnosť bola úspešne pridaná");
        }
        catch (Exception exception)
        {
            this.companyView.showErrorMessage("Novú spoločnosť sa nepodarilo pridať. Skontrolujte prosím správnosť a úplnosť zadaných údajov.");
        }
    }

    private void doUpdateCompany(CompanyUpdateEvent event)
    {
        this.companyDto = event.getCompanyDto();

        try
        {
            this.company = this.companyDto.toCompany(this.company);
            this.companyService.updateCompany(this.company);
            successOperation("Údaje spoločnosti boli úspešne zmenené");
        }
        catch (Exception exception)
        {
            this.companyView.showErrorMessage("Nepodarilo sa zneniť údaje spoločnosti. Skontrolujte prosím správnosť a úplnosť zadaných údajov.");
        }
    }

    private void doDeleteCompany(CompanyDeleteEvent event)
    {
        boolean correctDeleted = this.companyService.deleteCompany(this.company);


        if(correctDeleted)
        {
            successOperation("Údaje boli vymazané");
            this.companyView.fireEventOnThisView();
        }
        else
            this.companyView.showErrorMessage("Údaje nebolo možné vymazať");
    }

    private void cancelEdit(CompanyCancelEvent event)
    {
        this.clear();
    }

    private void successOperation(String successText)
    {
        this.clear();
        this.companyView.refreshTable();
        this.companyView.showSuccessOperationNotification(successText);
    }

    public void clear()
    {
        this.companyView.getCompanyEditor().clear();
        this.companyView.closeWindow();
        this.isNew = false;
        this.company = null;
        this.companyDto = null;
    }
}
