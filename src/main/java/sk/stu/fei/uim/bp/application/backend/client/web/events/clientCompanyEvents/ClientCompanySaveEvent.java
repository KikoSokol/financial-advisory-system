package sk.stu.fei.uim.bp.application.backend.client.web.events.clientCompanyEvents;

import sk.stu.fei.uim.bp.application.backend.client.web.editors.CompanyEditor;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.ClientCompanyDto;

public class ClientCompanySaveEvent extends ClientCompanyFormEvent
{
    public ClientCompanySaveEvent(CompanyEditor source, ClientCompanyDto clientCompanyDto)
    {
        super(source,clientCompanyDto);
    }
}
