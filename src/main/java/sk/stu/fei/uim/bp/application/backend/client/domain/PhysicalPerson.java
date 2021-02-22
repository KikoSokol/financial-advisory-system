package sk.stu.fei.uim.bp.application.backend.client.domain;

import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;

@Data
public class PhysicalPerson extends Person
{

    private List<ObjectId> clientCompanies;


    @Override
    public String toString() {
        return super.toString() + "PhysicalPerson{" +
                "\n clientCompanies=" + clientCompanies +
                "}\n\n";
    }
}
