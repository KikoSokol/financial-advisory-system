package sk.stu.fei.uim.bp.application.backend.contracts.web.events.vehicleInsuranceEvents;

import com.vaadin.flow.component.ComponentEvent;
import sk.stu.fei.uim.bp.application.backend.contracts.web.dto.VehicleInsuranceDto;
import sk.stu.fei.uim.bp.application.backend.contracts.web.editors.VehicleInsuranceEditor;
import sk.stu.fei.uim.bp.application.backend.file.FileWrapper;

import java.util.List;

public abstract class VehicleInsuranceEditorEvent extends ComponentEvent<VehicleInsuranceEditor>
{
    private final VehicleInsuranceDto vehicleInsuranceDto;
    private final List<FileWrapper> attachments;

    public VehicleInsuranceEditorEvent(VehicleInsuranceEditor source, VehicleInsuranceDto vehicleInsuranceDto, List<FileWrapper> attachments)
    {
        super(source,false);
        this.vehicleInsuranceDto = vehicleInsuranceDto;
        this.attachments = attachments;
    }

    public VehicleInsuranceDto getVehicleInsuranceDto() {
        return vehicleInsuranceDto;
    }

    public List<FileWrapper> getAttachments() {
        return attachments;
    }
}
