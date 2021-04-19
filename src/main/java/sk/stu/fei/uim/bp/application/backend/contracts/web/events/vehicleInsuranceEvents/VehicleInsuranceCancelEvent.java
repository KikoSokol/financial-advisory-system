package sk.stu.fei.uim.bp.application.backend.contracts.web.events.vehicleInsuranceEvents;

import sk.stu.fei.uim.bp.application.backend.contracts.web.dto.VehicleInsuranceDto;
import sk.stu.fei.uim.bp.application.backend.contracts.web.editors.VehicleInsuranceEditor;

public class VehicleInsuranceCancelEvent extends VehicleInsuranceEditorEvent
{
    public VehicleInsuranceCancelEvent(VehicleInsuranceEditor source, VehicleInsuranceDto vehicleInsuranceDto) {
        super(source, vehicleInsuranceDto);
    }
}
