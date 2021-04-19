package sk.stu.fei.uim.bp.application.backend.contracts.web.events.nonLifeInsuracneEvents;

import com.vaadin.flow.component.ComponentEvent;
import sk.stu.fei.uim.bp.application.backend.contracts.web.dto.NonLifeInsuranceDto;
import sk.stu.fei.uim.bp.application.backend.contracts.web.editors.NonLifeInsuranceEditor;

public abstract class NonLifeInsuranceEditorEvent extends ComponentEvent<NonLifeInsuranceEditor>
{
    private final NonLifeInsuranceDto nonLifeInsuranceDto;

    public NonLifeInsuranceEditorEvent(NonLifeInsuranceEditor source, NonLifeInsuranceDto nonLifeInsuranceDto)
    {
        super(source,false);
        this.nonLifeInsuranceDto = nonLifeInsuranceDto;
    }

    public NonLifeInsuranceDto getNonLifeInsuranceDto() {
        return nonLifeInsuranceDto;
    }
}
