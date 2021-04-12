package sk.stu.fei.uim.bp.application.backend.client.web.events.clientCompanyEvents;

import sk.stu.fei.uim.bp.application.backend.client.web.dto.ClientCompanyDto;
import sk.stu.fei.uim.bp.application.backend.client.web.editors.ClientCompanyEditor;

public class ClientCompanyCancelEvent extends ClientCompanyFormEvent
{
    public ClientCompanyCancelEvent(ClientCompanyEditor source, ClientCompanyDto clientCompanyDto) {
        super(source, clientCompanyDto);
    }
}
