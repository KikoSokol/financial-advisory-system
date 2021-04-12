package sk.stu.fei.uim.bp.application.backend.client.web.events.clientCompanyEvents;

import sk.stu.fei.uim.bp.application.backend.client.web.dto.ClientCompanyDto;
import sk.stu.fei.uim.bp.application.backend.client.web.editors.ClientCompanyEditor;

public class ClientCompanyUpdateEvent extends ClientCompanyFormEvent
{
    public ClientCompanyUpdateEvent(ClientCompanyEditor source, ClientCompanyDto clientCompanyDto) {
        super(source, clientCompanyDto);
    }
}
