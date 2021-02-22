package sk.stu.fei.uim.bp.application.backend.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Address
{
    private String street;
    private String numberOfHouse;
    private String postalCode;
    private String city;
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
