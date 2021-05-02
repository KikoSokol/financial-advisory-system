package sk.stu.fei.uim.bp.application.backend.contracts.web.events.lifeInsuranceEvents;

import sk.stu.fei.uim.bp.application.backend.contracts.web.dto.LifeInsuranceDto;
import sk.stu.fei.uim.bp.application.backend.contracts.web.editors.LifeInsuranceEditor;
import sk.stu.fei.uim.bp.application.backend.file.FileWrapper;

import java.util.List;

public class LifeInsuranceUpdateEvent extends LifeInsuranceEditorEvent
{
    public LifeInsuranceUpdateEvent(LifeInsuranceEditor source, LifeInsuranceDto lifeInsuranceDto, List<FileWrapper> attachments) {
        super(source, lifeInsuranceDto, attachments);
    }
}
