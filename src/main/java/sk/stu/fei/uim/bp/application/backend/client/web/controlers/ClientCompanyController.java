package sk.stu.fei.uim.bp.application.backend.client.web.controlers;

import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.address.Address;
import sk.stu.fei.uim.bp.application.backend.client.domain.ClientCompany;
import sk.stu.fei.uim.bp.application.backend.client.service.ClientCompanyService;
import sk.stu.fei.uim.bp.application.backend.client.service.implementation.ClientServiceImpl;
import sk.stu.fei.uim.bp.application.backend.client.web.ClientMainView;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.ClientCompanyDto;
import sk.stu.fei.uim.bp.application.backend.client.web.editors.CompanyEditor;
import sk.stu.fei.uim.bp.application.backend.client.web.events.clientCompanyEvents.ClientCompanySaveEvent;

public class ClientCompanyController extends MainClientController
{
    private final ClientCompanyService clientCompanyService;

    private boolean isNew;
    private ClientCompany clientCompany;
    private ClientCompanyDto clientCompanyDto;

    public ClientCompanyController(ClientMainView clientMainView, ObjectId currentAgentId, ClientServiceImpl clientService)
    {
        super(clientMainView,currentAgentId);
        this.clientCompanyService = clientService;
        initActionOfEditor();
        this.clear();
    }

    public void initActionOfEditor()
    {
        CompanyEditor companyEditor = this.clientMainView.getCompanyEditor();
        companyEditor.addListener(ClientCompanySaveEvent.class,this::doSaveNewClientCompany);

    }

    private void openEditor(ClientCompanyDto clientCompanyDto)
    {
        CompanyEditor editor = this.clientMainView.getCompanyEditor();
        editor.setClientCompanyDto(clientCompanyDto);
        this.clientMainView.showMainWindow(editor);
    }

    public void addNewClientCompany()
    {
        this.isNew = true;

        this.clientCompany = new ClientCompany();
        this.clientCompany.setAgent(super.currentAgentId);
        this.clientCompany.setAddress(new Address());
        this.clientCompanyDto = new ClientCompanyDto();

        openEditor(this.clientCompanyDto);
    }



    public void updateClientCompany(ClientCompany clientCompany)
    {
        this.isNew = false;
        this.clientCompany = clientCompany;
        this.clientCompanyDto = new ClientCompanyDto(this.clientCompany);

        openEditor(this.clientCompanyDto);
    }

    public void doSaveNewClientCompany(ClientCompanySaveEvent event)
    {
        this.clientCompanyDto = event.getClientCompanyDto();

        try {
            this.clientCompany = this.clientCompanyDto.toClientCompany(this.clientCompany);
            this.clientCompanyService.addNewClientCompany(this.clientCompany);
            this.clear();
            super.clientMainView.refreshTable();
        }
        catch (Exception exception)
        {
            System.out.println("Nepodarilo sa pridať spoločnosť (ClientCompanyController)");
        }
    }

    public void clear()
    {
        super.clientMainView.getCompanyEditor().clear();
        super.clientMainView.closeMainWindow();
        this.isNew = false;
        this.clientCompany = null;
        this.clientCompanyDto = null;
    }
}
