package sk.stu.fei.uim.bp.application.backend.client.web.events.clientCompanyEvents;

import com.vaadin.flow.component.ComponentEvent;
import sk.stu.fei.uim.bp.application.backend.client.web.editors.CompanyEditor;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.ClientCompanyDto;

public abstract class ClientCompanyFormEvent extends ComponentEvent<CompanyEditor>
{
    private ClientCompanyDto clientCompanyDto;

    public ClientCompanyFormEvent(CompanyEditor source, ClientCompanyDto clientCompanyDto)
    {
        super(source,false);
        this.clientCompanyDto = clientCompanyDto;
    }

    public ClientCompanyDto getClientCompanyDto()
    {
        return this.clientCompanyDto;
    }

}
