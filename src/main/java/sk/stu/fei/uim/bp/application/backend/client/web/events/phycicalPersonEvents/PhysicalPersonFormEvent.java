package sk.stu.fei.uim.bp.application.backend.client.web.events.phycicalPersonEvents;

import com.vaadin.flow.component.ComponentEvent;
import sk.stu.fei.uim.bp.application.backend.client.web.PhysicalPersonEditor;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.PhysicalPersonDto;


public abstract class PhysicalPersonFormEvent extends ComponentEvent<PhysicalPersonEditor>
{
    private PhysicalPersonDto physicalPersonDto;

    protected PhysicalPersonFormEvent(PhysicalPersonEditor source, PhysicalPersonDto physicalPersonDto) {
        super(source, false);
        this.physicalPersonDto = physicalPersonDto;
    }

    public PhysicalPersonDto getPhysicalPersonDto()
    {
        return this.physicalPersonDto;
    }
}
