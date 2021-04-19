package sk.stu.fei.uim.bp.application.backend.contracts.web.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.ClientDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.ProductDto;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.LifeInsurance;

@EqualsAndHashCode(callSuper = true)
@Data
public class LifeInsuranceDto extends InsuranceDto
{
    public LifeInsuranceDto(LifeInsurance lifeInsurance, ClientDto owner, ClientDto insured, ProductDto productDto) {
        super(lifeInsurance, owner, insured, productDto);
    }

    public LifeInsurance toLifeInsurance(LifeInsurance lifeInsurance)
    {
        super.toInsurance(lifeInsurance);

        return lifeInsurance;
    }
}
