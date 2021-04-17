package sk.stu.fei.uim.bp.application.backend.contracts.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LifeInsurance extends Insurance
{


    @Override
    public String toString() {
        return super.toString() + "\n LifeInsurance{}";
    }
}
