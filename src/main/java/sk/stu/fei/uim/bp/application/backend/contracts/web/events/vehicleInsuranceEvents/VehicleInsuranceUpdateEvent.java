package sk.stu.fei.uim.bp.application.backend.contracts.web.events.vehicleInsuranceEvents;

import sk.stu.fei.uim.bp.application.backend.contracts.web.dto.VehicleInsuranceDto;
import sk.stu.fei.uim.bp.application.backend.contracts.web.editors.VehicleInsuranceEditor;
import sk.stu.fei.uim.bp.application.backend.file.FileWrapper;

import java.util.List;

public class VehicleInsuranceUpdateEvent extends VehicleInsuranceEditorEvent
{
    public VehicleInsuranceUpdateEvent(VehicleInsuranceEditor source, VehicleInsuranceDto vehicleInsuranceDto, List<FileWrapper> attachments) {
        super(source, vehicleInsuranceDto,attachments);
    }
}
