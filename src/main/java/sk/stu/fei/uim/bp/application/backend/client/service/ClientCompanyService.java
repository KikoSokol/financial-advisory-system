package sk.stu.fei.uim.bp.application.backend.client.service;

import org.jetbrains.annotations.NotNull;
import sk.stu.fei.uim.bp.application.backend.client.domain.ClientCompany;

public interface ClientCompanyService
{
    ClientCompany addNewClientCompany(@NotNull ClientCompany newClientCompany);

    ClientCompany updateClientCompany(@NotNull ClientCompany upadateClientCompany);


}
