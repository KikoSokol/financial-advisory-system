package sk.stu.fei.uim.bp.application.backend.contracts.web.events.lifeInsuranceEvents;


import com.vaadin.flow.component.ComponentEvent;
import sk.stu.fei.uim.bp.application.backend.contracts.web.dto.LifeInsuranceDto;
import sk.stu.fei.uim.bp.application.backend.contracts.web.editors.LifeInsuranceEditor;

public abstract class LifeInsuranceEditorEvent extends ComponentEvent<LifeInsuranceEditor>
{
    private final LifeInsuranceDto lifeInsuranceDto;

    public LifeInsuranceEditorEvent(LifeInsuranceEditor source, LifeInsuranceDto lifeInsuranceDto) {
        super(source, false);
        this.lifeInsuranceDto = lifeInsuranceDto;
    }

    public LifeInsuranceDto getLifeInsuranceDto() {
        return lifeInsuranceDto;
    }
}
