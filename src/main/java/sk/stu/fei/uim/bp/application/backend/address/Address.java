package sk.stu.fei.uim.bp.application.backend.address;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class Address
{
    @NotBlank(message = "Ulica je povinné pole")
    private String street;

    @NotBlank(message = "Číslo domu je povinné pole")
    @Pattern(regexp = "[0-9]*[/]?[0-9]{1,}")
    private String numberOfHouse;


    @NotBlank(message = "PSČ je povinné pole")
    @Pattern(regexp = "[0-9]{3}[\\s]?[0-9]{2}")
    private String postalCode;


    @NotBlank(message = "Mesto je povinné pole")
    @Pattern(regexp = "^[a-zA-ZáäčéíóôúýčšžÁÄČÉÍÓÔÚÝČŠŽ ]+$", message = "Mesto musí obsahovať iba platné znaky")
    private String city;

    @NotBlank(message = "Krajina je povinné pole")
    @Pattern(regexp = "^[a-zA-ZáäčéíóôúýčšžÁÄČÉÍÓÔÚÝČŠŽ ]+$", message = "Krajina musí obsahovať iba platné znaky")
    private String state;


    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", numberOfHouse='" + numberOfHouse + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
