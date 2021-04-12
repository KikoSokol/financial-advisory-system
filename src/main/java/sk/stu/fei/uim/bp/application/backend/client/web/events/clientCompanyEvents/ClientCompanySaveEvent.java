package sk.stu.fei.uim.bp.application.backend.client.web.events.clientCompanyEvents;

import sk.stu.fei.uim.bp.application.backend.client.web.editors.ClientCompanyEditor;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.ClientCompanyDto;

public class ClientCompanySaveEvent extends ClientCompanyFormEvent
{
    public ClientCompanySaveEvent(ClientCompanyEditor source, ClientCompanyDto clientCompanyDto)
    {
        super(source,clientCompanyDto);
    }
}
