package sk.stu.fei.uim.bp.application.backend.contracts.web.events.lifeInsuranceEvents;


import com.vaadin.flow.component.ComponentEvent;
import sk.stu.fei.uim.bp.application.backend.contracts.web.dto.LifeInsuranceDto;
import sk.stu.fei.uim.bp.application.backend.contracts.web.editors.LifeInsuranceEditor;
import sk.stu.fei.uim.bp.application.backend.file.FileWrapper;

import java.util.List;

public abstract class LifeInsuranceEditorEvent extends ComponentEvent<LifeInsuranceEditor>
{
    private final LifeInsuranceDto lifeInsuranceDto;
    private final List<FileWrapper> attachments;

    public LifeInsuranceEditorEvent(LifeInsuranceEditor source, LifeInsuranceDto lifeInsuranceDto, List<FileWrapper> attachments) {
        super(source, false);
        this.lifeInsuranceDto = lifeInsuranceDto;
        this.attachments = attachments;
    }

    public LifeInsuranceDto getLifeInsuranceDto() {
        return lifeInsuranceDto;
    }

    public List<FileWrapper> getAttachments() {
        return attachments;
    }
}
