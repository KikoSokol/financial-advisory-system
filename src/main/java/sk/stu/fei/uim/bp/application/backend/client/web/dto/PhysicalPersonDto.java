package sk.stu.fei.uim.bp.application.backend.client.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import sk.stu.fei.uim.bp.application.backend.client.domain.PhysicalPerson;

@Data
@NoArgsConstructor
public class PhysicalPersonDto extends PersonDto
{
    public PhysicalPersonDto(PhysicalPerson physicalPerson)
    {
        super(physicalPerson);
    }

    public PhysicalPerson toPhysicalPerson(PhysicalPerson physicalPerson)
    {
        toPerson(physicalPerson);

        return physicalPerson;
    }


    @Override
    public String toString() {
        return super.toString() + "PhysicalPersonDto{}";
    }
}
