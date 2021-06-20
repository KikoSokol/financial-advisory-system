package sk.stu.fei.uim.bp.application.backend.calendar.web.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.address.Address;
import sk.stu.fei.uim.bp.application.backend.calendar.domain.Meeting;
import sk.stu.fei.uim.bp.application.backend.calendar.domain.MeetingAddress;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.ClientDto;

import javax.validation.constraints.Pattern;

@EqualsAndHashCode(callSuper = true)
@Data
public class MeetingDto extends EventDto
{

    private String street;

    @Pattern(regexp = "[0-9]*[/]?[0-9]{1,}",message = "Číslo domu musí byť v správnom tvare")
    private String numberOfHouse;

    @Pattern(regexp = "[0-9]{3}[\\s]?[0-9]{2}",message = "PSČ musí byť v správnom tvare")
    private String postalCode;

    @Pattern(regexp = "[^0-9]{1,}", message = "Mesto musí obsahovať iba platné znaky")
    private String city;

    @Pattern(regexp = "[^0-9]{1,}", message = "Krajina musí obsahovať iba platné znaky")
    private String state;

    private ClientDto clientDto;

    public MeetingDto(Meeting meeting, ClientDto clientDto)
    {
        super(meeting);
        initializeThisAddressField(meeting.getAddress());
        setClientDto(clientDto);

    }

    private void initializeThisAddressField(MeetingAddress address)
    {
        setStreet(address.getStreet());
        setNumberOfHouse(address.getNumberOfHouse());
        setPostalCode(address.getPostalCode());
        setCity(address.getCity());
        setState(address.getState());
    }

    protected MeetingAddress setAddressObject(MeetingAddress address)
    {
        address.setStreet(getStreet());
        address.setNumberOfHouse(getNumberOfHouse());
        address.setPostalCode(getPostalCode());
        address.setCity(getCity());
        address.setState(getState());

        return address;
    }

    public Meeting toMeeting(Meeting meeting)
    {
        super.toEvent(meeting);

        setAddressObject(meeting.getAddress());

        if(this.clientDto != null)
            meeting.setClientId(this.clientDto.getClientId());

        return meeting;
    }
}
