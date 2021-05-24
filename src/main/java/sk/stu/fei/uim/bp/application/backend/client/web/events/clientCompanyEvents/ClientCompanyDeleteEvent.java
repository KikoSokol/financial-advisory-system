package sk.stu.fei.uim.bp.application.backend.client.web.events.clientCompanyEvents;

import sk.stu.fei.uim.bp.application.backend.client.web.dto.ClientCompanyDto;
import sk.stu.fei.uim.bp.application.backend.client.web.editors.ClientCompanyEditor;


public class ClientCompanyDeleteEvent extends ClientCompanyFormEvent
{
    public ClientCompanyDeleteEvent(ClientCompanyEditor source, ClientCompanyDto physicalPersonDto)
    {
        super(source,physicalPersonDto);
    }
}
