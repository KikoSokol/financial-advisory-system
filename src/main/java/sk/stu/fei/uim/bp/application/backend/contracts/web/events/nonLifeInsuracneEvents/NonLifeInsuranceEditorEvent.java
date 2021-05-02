package sk.stu.fei.uim.bp.application.backend.contracts.web.events.nonLifeInsuracneEvents;

import com.vaadin.flow.component.ComponentEvent;
import sk.stu.fei.uim.bp.application.backend.contracts.web.dto.NonLifeInsuranceDto;
import sk.stu.fei.uim.bp.application.backend.contracts.web.editors.NonLifeInsuranceEditor;
import sk.stu.fei.uim.bp.application.backend.file.FileWrapper;

import java.util.List;

public abstract class NonLifeInsuranceEditorEvent extends ComponentEvent<NonLifeInsuranceEditor>
{
    private final NonLifeInsuranceDto nonLifeInsuranceDto;
    private final List<FileWrapper> attachments;

    public NonLifeInsuranceEditorEvent(NonLifeInsuranceEditor source, NonLifeInsuranceDto nonLifeInsuranceDto, List<FileWrapper> attachments)
    {
        super(source,false);
        this.nonLifeInsuranceDto = nonLifeInsuranceDto;
        this.attachments = attachments;
    }

    public NonLifeInsuranceDto getNonLifeInsuranceDto() {
        return nonLifeInsuranceDto;
    }

    public List<FileWrapper> getAttachments() {
        return attachments;
    }
}
