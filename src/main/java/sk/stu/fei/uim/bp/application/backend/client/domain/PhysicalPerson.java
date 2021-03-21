package sk.stu.fei.uim.bp.application.backend.client.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;

import java.util.LinkedList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PhysicalPerson extends Person
{

    private List<ObjectId> clientCompanies = new LinkedList<>();


    @Override
    public String toString() {
        return super.toString() + "PhysicalPerson{" +
                "\n clientCompanies=" + clientCompanies +
                "}\n\n";
    }
}
