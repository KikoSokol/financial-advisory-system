package sk.stu.fei.uim.bp.application.backend.contracts.web.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.ClientDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.ProductDto;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.VehicleInsurance;

import javax.validation.constraints.NotNull;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class VehicleInsuranceDto extends NonLifeInsuranceDto
{
    @NotNull(message = "ŠPZ auta je povinný údaj")
    private String numberOfVehicle;

    public VehicleInsuranceDto(VehicleInsurance vehicleInsurance, ClientDto owner, ClientDto insured, ProductDto productDto) {
        super(vehicleInsurance, owner, insured, productDto);
        setNumberOfVehicle(vehicleInsurance.getNumberOfVehicle());
    }

    public VehicleInsurance toVehicleInsurance(VehicleInsurance vehicleInsurance)
    {
        super.toNonLifeInsurance(vehicleInsurance);
        vehicleInsurance.setNumberOfVehicle(getNumberOfVehicle());

        return vehicleInsurance;
    }

}
