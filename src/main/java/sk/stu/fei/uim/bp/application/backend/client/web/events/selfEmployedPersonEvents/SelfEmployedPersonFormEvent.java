package sk.stu.fei.uim.bp.application.backend.client.web.events.selfEmployedPersonEvents;

import com.vaadin.flow.component.ComponentEvent;
import sk.stu.fei.uim.bp.application.backend.client.web.editors.SelfEmployedPersonEditor;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.SelfEmployedPersonDto;

public abstract class SelfEmployedPersonFormEvent extends ComponentEvent<SelfEmployedPersonEditor>
{
    private SelfEmployedPersonDto selfEmployedPersonDto;

    public SelfEmployedPersonFormEvent(SelfEmployedPersonEditor source, SelfEmployedPersonDto selfEmployedPersonDto) {
        super(source, false);
        this.selfEmployedPersonDto = selfEmployedPersonDto;
    }

    public SelfEmployedPersonDto getSelfEmployedPersonDto()
    {
        return this.selfEmployedPersonDto;
    }
}
