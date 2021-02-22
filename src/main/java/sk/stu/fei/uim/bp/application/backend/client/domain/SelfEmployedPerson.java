package sk.stu.fei.uim.bp.application.backend.client.domain;

import lombok.Data;

@Data
public class SelfEmployedPerson extends Person
{
    private String ico;

    private String businessName;

    private String businessObject;


    @Override
    public String toString() {
        return super.toString() + "SelfEmployedPerson{" +
                "\n ico=" + ico +
                "\n businessName='" + businessName + '\'' +
                "\n businessObject='" + businessObject + '\'' +
                "}\n\n";
    }
}
