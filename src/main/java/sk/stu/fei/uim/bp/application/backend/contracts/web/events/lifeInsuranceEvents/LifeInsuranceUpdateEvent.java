package sk.stu.fei.uim.bp.application.backend.contracts.web.events.lifeInsuranceEvents;

import sk.stu.fei.uim.bp.application.backend.contracts.web.dto.LifeInsuranceDto;
import sk.stu.fei.uim.bp.application.backend.contracts.web.editors.LifeInsuranceEditor;

public class LifeInsuranceUpdateEvent extends LifeInsuranceEditorEvent
{
    public LifeInsuranceUpdateEvent(LifeInsuranceEditor source, LifeInsuranceDto lifeInsuranceDto) {
        super(source, lifeInsuranceDto);
    }
}
