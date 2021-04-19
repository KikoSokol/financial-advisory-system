package sk.stu.fei.uim.bp.application.backend.contracts.web.events.vehicleInsuranceEvents;

import com.vaadin.flow.component.ComponentEvent;
import sk.stu.fei.uim.bp.application.backend.contracts.web.dto.VehicleInsuranceDto;
import sk.stu.fei.uim.bp.application.backend.contracts.web.editors.VehicleInsuranceEditor;

public abstract class VehicleInsuranceEditorEvent extends ComponentEvent<VehicleInsuranceEditor>
{
    private final VehicleInsuranceDto vehicleInsuranceDto;

    public VehicleInsuranceEditorEvent(VehicleInsuranceEditor source, VehicleInsuranceDto vehicleInsuranceDto)
    {
        super(source,false);
        this.vehicleInsuranceDto = vehicleInsuranceDto;
    }

    public VehicleInsuranceDto getVehicleInsuranceDto() {
        return vehicleInsuranceDto;
    }
}
