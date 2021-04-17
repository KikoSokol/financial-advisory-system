package sk.stu.fei.uim.bp.application.backend.contracts.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class VehicleInsurance extends NonLifeInsurance
{
    @NotNull(message = "ŠPZ auta je povinný údaj")
    private String numberOfVehicle;


    @Override
    public String toString() {
        return super.toString() + "\n VehicleInsurance{" +
                "\n numberOfVehicle='" + numberOfVehicle + '\'' +
                '}';
    }
}
