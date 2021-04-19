package sk.stu.fei.uim.bp.application.backend.contracts.web.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.ClientDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.ProductDto;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.NonLifeInsurance;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class NonLifeInsuranceDto extends InsuranceDto
{
    public NonLifeInsuranceDto(NonLifeInsurance nonLifeInsurance, ClientDto owner, ClientDto insured, ProductDto productDto) {
        super(nonLifeInsurance, owner, insured, productDto);
    }

    public NonLifeInsurance toNonLifeInsurance(NonLifeInsurance nonLifeInsurance)
    {
        super.toInsurance(nonLifeInsurance);

        return nonLifeInsurance;
    }
}
