package sk.stu.fei.uim.bp.application.backend.client.web.controlers;

import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.address.Address;
import sk.stu.fei.uim.bp.application.backend.client.domain.ClientCompany;
import sk.stu.fei.uim.bp.application.backend.client.domain.PhysicalPerson;
import sk.stu.fei.uim.bp.application.backend.client.service.ClientCompanyService;
import sk.stu.fei.uim.bp.application.backend.client.service.ClientService;
import sk.stu.fei.uim.bp.application.backend.client.service.PhysicalPersonService;
import sk.stu.fei.uim.bp.application.backend.client.service.implementation.ClientServiceImpl;
import sk.stu.fei.uim.bp.application.backend.client.web.ClientMainView;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.ClientCompanyDto;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.PhysicalPersonDto;
import sk.stu.fei.uim.bp.application.backend.client.web.editors.ClientCompanyEditor;
import sk.stu.fei.uim.bp.application.backend.client.web.events.clientCompanyEvents.ClientCompanyCancelEvent;
import sk.stu.fei.uim.bp.application.backend.client.web.events.clientCompanyEvents.ClientCompanyDeleteEvent;
import sk.stu.fei.uim.bp.application.backend.client.web.events.clientCompanyEvents.ClientCompanySaveEvent;
import sk.stu.fei.uim.bp.application.backend.client.web.events.clientCompanyEvents.ClientCompanyUpdateEvent;

import java.util.LinkedList;
import java.util.List;

public class ClientCompanyController extends MainClientController
{
    private final ClientCompanyService clientCompanyService;
    private final PhysicalPersonService physicalPersonService;
    private final ClientService clientService;

    private boolean isNew;
    private ClientCompany clientCompany;
    private ClientCompanyDto clientCompanyDto;

    public ClientCompanyController(ClientMainView clientMainView, ObjectId currentAgentId, ClientServiceImpl clientService)
    {
        super(clientMainView,currentAgentId);
        this.clientCompanyService = clientService;
        this.physicalPersonService = clientService;
        this.clientService = clientService;
        initActionOfEditor();
        this.clear();
    }

    public void initActionOfEditor()
    {
        ClientCompanyEditor clientCompanyEditor = this.clientMainView.getCompanyEditor();
        clientCompanyEditor.addListener(ClientCompanySaveEvent.class,this::doSaveNewClientCompany);
        clientCompanyEditor.addListener(ClientCompanyUpdateEvent.class,this::doUpdateClientCompany);
        clientCompanyEditor.addListener(ClientCompanyCancelEvent.class,this::cancelEdit);
        clientCompanyEditor.addListener(ClientCompanyDeleteEvent.class,this::doDeleteClientCompany);

    }

    private void openEditor(ClientCompanyDto clientCompanyDto, boolean isNew)
    {
        ClientCompanyEditor editor = this.clientMainView.getCompanyEditor();
        editor.setClientCompanyDto(clientCompanyDto,isNew);
        this.clientMainView.showMainWindow(editor);
    }

    public void addNewClientCompany()
    {
        this.isNew = true;

        this.clientCompany = new ClientCompany();
        this.clientCompany.setAgent(super.currentAgentId);
        this.clientCompany.setAddress(new Address());
        this.clientCompanyDto = new ClientCompanyDto();

        openEditor(this.clientCompanyDto,this.isNew);
    }



    public void updateClientCompany(ClientCompany clientCompany)
    {
        this.isNew = false;
        this.clientCompany = clientCompany;
        this.clientCompanyDto = new ClientCompanyDto(this.clientCompany,getManagersDto(this.clientCompany.getManagers()));

        openEditor(this.clientCompanyDto,this.isNew);
    }

    private void doSaveNewClientCompany(ClientCompanySaveEvent event)
    {
        this.clientCompanyDto = event.getClientCompanyDto();

        try {
            this.clientCompany = this.clientCompanyDto.toClientCompany(this.clientCompany);
            this.clientCompanyService.addNewClientCompany(this.clientCompany);
            successOperation("Nový klient bol úspešne pridaný");
        }
        catch (Exception exception)
        {
            super.clientMainView.showErrorMessage("Klienta sa nepodarilo pridať. Skontrolujte prosím správnosť a úplnosť zadaných údajov.");
        }
    }

    private void doUpdateClientCompany(ClientCompanyUpdateEvent event)
    {
        this.clientCompanyDto = event.getClientCompanyDto();

        try {
            this.clientCompany = this.clientCompanyDto.toClientCompany(this.clientCompany);
            this.clientCompanyService.updateClientCompany(this.clientCompany);
            successOperation("Údaje klienta boli úspešne zmenené");
        }
        catch (Exception exception)
        {
            super.clientMainView.showErrorMessage("Klientovi sa nepodarilo zmeniť údaje. Skontrolujte prosím správnosť a úplnosť zadaných údajov.");
        }
    }

    private void doDeleteClientCompany(ClientCompanyDeleteEvent event)
    {
        boolean correctDeleted = this.clientService.deleteClient(this.clientCompany);

        if(correctDeleted)
        {
            successOperation("Údaje boli vymazané");
        }
        else
            super.clientMainView.showErrorMessage("Údaje nebolo možné vymazať");
    }


    private void cancelEdit(ClientCompanyCancelEvent event)
    {
        this.clear();
    }

    public void clear()
    {
        super.clientMainView.getCompanyEditor().clear();
        super.clientMainView.closeMainWindow();
        this.isNew = false;
        this.clientCompany = null;
        this.clientCompanyDto = null;
    }

    private void successOperation(String successText)
    {
        this.clear();
        super.clientMainView.refreshTable();
        super.clientMainView.showSuccessOperationClientNotification(successText);
    }


    private List<PhysicalPersonDto> getManagersDto(List<ObjectId> managers)
    {
        List<PhysicalPerson> physicalPeople = this.physicalPersonService.getAllPhysicalPersonsByListOfIds(managers);

        List<PhysicalPersonDto> dtos = new LinkedList<>();

        for(PhysicalPerson physicalPerson : physicalPeople)
        {
            dtos.add(new PhysicalPersonDto(physicalPerson));
        }

        return dtos;
    }
}
