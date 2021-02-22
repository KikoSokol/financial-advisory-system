package sk.stu.fei.uim.bp.application.backend.client.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
public class IdentifyCardCopyReference
{

    private ObjectId frontSide;
    private ObjectId backSide;

    public IdentifyCardCopyReference(ObjectId frontSide,ObjectId backSide)
    {
        setFrontSide(frontSide);
        setBackSide(backSide);
    }

    @Override
    public String toString() {
        return "IdentifyCardCopyReference{" +
                "frontSide=" + frontSide +
                ", backSide=" + backSide +
                '}';
    }
}
