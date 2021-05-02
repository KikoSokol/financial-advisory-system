package sk.stu.fei.uim.bp.application.backend.contracts.web.events.nonLifeInsuracneEvents;

import sk.stu.fei.uim.bp.application.backend.contracts.web.dto.NonLifeInsuranceDto;
import sk.stu.fei.uim.bp.application.backend.contracts.web.editors.NonLifeInsuranceEditor;
import sk.stu.fei.uim.bp.application.backend.file.FileWrapper;

import java.util.List;

public class NonLifeInsuranceUpdateEvent extends NonLifeInsuranceEditorEvent
{
    public NonLifeInsuranceUpdateEvent(NonLifeInsuranceEditor source, NonLifeInsuranceDto nonLifeInsuranceDto, List<FileWrapper> attachments) {
        super(source, nonLifeInsuranceDto,attachments);
    }
}
