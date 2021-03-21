package sk.stu.fei.uim.bp.application.backend.client.web.events.phycicalPersonEvents;

import sk.stu.fei.uim.bp.application.backend.client.web.editors.PhysicalPersonEditor;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.PhysicalPersonDto;

public class PhysicalPersonSaveEvent extends PhysicalPersonFormEvent
{
    public PhysicalPersonSaveEvent(PhysicalPersonEditor source, PhysicalPersonDto physicalPersonDto)
    {
        super(source,physicalPersonDto);
    }
}
