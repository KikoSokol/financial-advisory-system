package sk.stu.fei.uim.bp.application.backend.contracts.web.events.nonLifeInsuracneEvents;

import sk.stu.fei.uim.bp.application.backend.contracts.web.dto.NonLifeInsuranceDto;
import sk.stu.fei.uim.bp.application.backend.contracts.web.editors.NonLifeInsuranceEditor;

public class NonLifeInsuranceUpdateEvent extends NonLifeInsuranceEditorEvent
{
    public NonLifeInsuranceUpdateEvent(NonLifeInsuranceEditor source, NonLifeInsuranceDto nonLifeInsuranceDto) {
        super(source, nonLifeInsuranceDto);
    }
}
