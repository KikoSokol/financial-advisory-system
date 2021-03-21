package sk.stu.fei.uim.bp.application.backend.client.web.events.selfEmployedPersonEvents;

import sk.stu.fei.uim.bp.application.backend.client.web.editors.SelfEmployedPersonEditor;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.SelfEmployedPersonDto;

public class SelfEmployedPersonSaveEvent extends SelfEmployedPersonFormEvent
{

    public SelfEmployedPersonSaveEvent(SelfEmployedPersonEditor source, SelfEmployedPersonDto selfEmployedPersonDto) {
        super(source, selfEmployedPersonDto);
    }
}
