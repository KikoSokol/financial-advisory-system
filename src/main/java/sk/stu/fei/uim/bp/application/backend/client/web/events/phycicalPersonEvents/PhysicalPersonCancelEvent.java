package sk.stu.fei.uim.bp.application.backend.client.web.events.phycicalPersonEvents;

import sk.stu.fei.uim.bp.application.backend.client.web.dto.PhysicalPersonDto;
import sk.stu.fei.uim.bp.application.backend.client.web.editors.PhysicalPersonEditor;

public class PhysicalPersonCancelEvent extends PhysicalPersonFormEvent
{

    public PhysicalPersonCancelEvent(PhysicalPersonEditor source, PhysicalPersonDto physicalPersonDto) {
        super(source, physicalPersonDto);
    }
}
