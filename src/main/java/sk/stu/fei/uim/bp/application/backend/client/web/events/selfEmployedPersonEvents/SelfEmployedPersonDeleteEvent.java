package sk.stu.fei.uim.bp.application.backend.client.web.events.selfEmployedPersonEvents;

import sk.stu.fei.uim.bp.application.backend.client.web.dto.SelfEmployedPersonDto;
import sk.stu.fei.uim.bp.application.backend.client.web.editors.SelfEmployedPersonEditor;

public class SelfEmployedPersonDeleteEvent extends SelfEmployedPersonFormEvent
{
    public SelfEmployedPersonDeleteEvent(SelfEmployedPersonEditor source, SelfEmployedPersonDto physicalPersonDto)
    {
        super(source,physicalPersonDto);
    }
}
