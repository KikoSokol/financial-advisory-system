package sk.stu.fei.uim.bp.application.backend.client.web.events.selfEmployedPersonEvents;

import sk.stu.fei.uim.bp.application.backend.client.web.dto.SelfEmployedPersonDto;
import sk.stu.fei.uim.bp.application.backend.client.web.editors.SelfEmployedPersonEditor;

public class SelfEmployedPersonCancelEvent extends SelfEmployedPersonFormEvent
{

    public SelfEmployedPersonCancelEvent(SelfEmployedPersonEditor source, SelfEmployedPersonDto selfEmployedPersonDto) {
        super(source, selfEmployedPersonDto);
    }
}
