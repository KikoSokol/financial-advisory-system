package sk.stu.fei.uim.bp.application.backend.calendar.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class MeetingAddress
{

    private String street;

    @Pattern(regexp = "[0-9]*[/]?[0-9]{1,}")
    private String numberOfHouse;

    @Pattern(regexp = "[0-9]{3}[\\s]?[0-9]{2}")
    private String postalCode;

    @Pattern(regexp = "[^0-9]{1,}", message = "Mesto musí obsahovať iba platné znaky")
    private String city;

    @Pattern(regexp = "[^0-9]{1,}", message = "Krajina musí obsahovať iba platné znaky")
    private String state;
}
