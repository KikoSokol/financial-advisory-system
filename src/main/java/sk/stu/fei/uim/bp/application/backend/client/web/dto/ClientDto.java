package sk.stu.fei.uim.bp.application.backend.client.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.address.Address;
import sk.stu.fei.uim.bp.application.backend.client.domain.Client;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Data
@NoArgsConstructor
public abstract class ClientDto
{
    private ObjectId clientId;

    @NotBlank(message = "Ulica je povinné pole")
    private String street;

    @NotBlank(message = "Číslo domu je povinné pole")
    @Pattern(regexp = "[0-9]*[/]?[0-9]{1,}",message = "Číslo domu muí byť v správnom tvare")
    private String numberOfHouse;


    @NotBlank(message = "PSČ je povinné pole")
    @Pattern(regexp = "[0-9]{3}[\\s]?[0-9]{2}",message = "PSČ musí byť v správnom tvare")
    private String postalCode;


    @NotBlank(message = "Mesto je povinné pole")
    @Pattern(regexp = "[^0-9]{1,}", message = "Mesto musí obsahovať iba platné znaky")
    private String city;

    @NotBlank(message = "Krajina je povinné pole")
    @Pattern(regexp = "[^0-9]{1,}", message = "Krajina musí obsahovať iba platné znaky")
    private String state;

    private String note;

    protected ClientDto(Client client)
    {
        setClientId(client.getClientId());
        initializeThisAdressField(client.getAddress());
        setNote(client.getNote());
    }

    private void initializeThisAdressField(Address address)
    {
        setStreet(address.getStreet());
        setNumberOfHouse(address.getNumberOfHouse());
        setPostalCode(address.getPostalCode());
        setCity(address.getCity());
        setState(address.getState());
    }

    protected Address setAddressObject(Address address)
    {
        address.setStreet(getStreet());
        address.setNumberOfHouse(getNumberOfHouse());
        address.setPostalCode(getPostalCode());
        address.setCity(getCity());
        address.setState(getState());

        return address;
    }

    protected Client toClient(Client client)
    {
        setAddressObject(client.getAddress());
        client.setNote(getNote());
        return client;
    }


    @Override
    public String toString() {
        return "ClientDto{" +
                "clientId='" + clientId + '\'' +
                "street='" + street + '\'' +
                ", numberOfHouse='" + numberOfHouse + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", note='" + note + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientDto)) return false;
        ClientDto clientDto = (ClientDto) o;
        return getClientId().equals(clientDto.getClientId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClientId());
    }

    public abstract String getFullName();
}
