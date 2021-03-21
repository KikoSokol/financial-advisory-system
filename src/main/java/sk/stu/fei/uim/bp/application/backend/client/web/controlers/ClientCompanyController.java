package sk.stu.fei.uim.bp.application.backend.client.web.controlers;

import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.address.Address;
import sk.stu.fei.uim.bp.application.backend.client.domain.ClientCompany;
import sk.stu.fei.uim.bp.application.backend.client.domain.PhysicalPerson;
import sk.stu.fei.uim.bp.application.backend.client.service.ClientCompanyService;
import sk.stu.fei.uim.bp.application.backend.client.service.implementation.ClientServiceImpl;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.ClientCompanyDto;

public class ClientCompanyController
{
    private final ClientCompanyService clientCompanyService;

    private boolean isNew;
    private ClientCompany clientCompany;
    private ClientCompanyDto clientCompanyDto;

    public ClientCompanyController(ClientServiceImpl clientService)
    {
        this.clientCompanyService = clientService;
        this.clear();
    }

    public ClientCompanyDto addNewClientCompany(ObjectId currentAgent)
    {
        this.isNew = true;
        this.clientCompany = new ClientCompany();
        this.clientCompany.setAgent(currentAgent);
        this.clientCompany.setAddress(new Address());
        this.clientCompanyDto = new ClientCompanyDto();

        return this.clientCompanyDto;
    }

    public ClientCompanyDto updateClientCompany(ClientCompany clientCompany)
    {
        this.isNew = false;
        this.clientCompany = clientCompany;
        this.clientCompanyDto = new ClientCompanyDto(this.clientCompany);

        return this.clientCompanyDto;
    }

    public ClientCompany doSaveNewClientCompany(ClientCompanyDto clientCompanyDto) throws Exception
    {
        this.clientCompanyDto = clientCompanyDto;

        try {
            this.clientCompany = this.clientCompanyDto.toClientCompany(this.clientCompany);
            return this.clientCompanyService.addNewClientCompany(this.clientCompany);
        }
        catch (Exception exception)
        {
            System.out.println("Nepodarilo sa pridať spoločnosť (ClientCompanyController)");
            throw exception;
        }
    }

    public void clear()
    {
        this.isNew = false;
        this.clientCompany = null;
        this.clientCompanyDto = null;
    }
}
