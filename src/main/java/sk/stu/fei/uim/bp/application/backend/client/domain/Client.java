package sk.stu.fei.uim.bp.application.backend.client.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import sk.stu.fei.uim.bp.application.backend.address.Address;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@Document("client")
public abstract class Client
{
    @MongoId
    private ObjectId clientId;

    @NotNull
    private Address address;

    @NotNull
    private ObjectId agent;

    private String note;

    private List<ObjectId> contracts = new LinkedList<>();


    public boolean addContract(ObjectId contract)
    {
        if(contract == null)
            contracts = new LinkedList<>();

        return contracts.add(contract);
    }

    public boolean removeContract(ObjectId contract)
    {
        return contracts.remove(contract);
    }


    @Override
    public String toString() {
        return "Client{" +
                "\n clientId= " + clientId +
                "\n address= " + address +
                "\n agent= " + agent +
                "\n note='" + note + '\'' +
                "\n contracts=" + contracts +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return getClientId().equals(client.getClientId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClientId());
    }
}
