package sk.stu.fei.uim.bp.application.backend.contracts.repository;

import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.Contract;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface ContractRepository
{

    Contract addNewContract(@NotNull Contract newContract);

    void deleteContract(@NotNull Contract contractToDelete);

    Contract updateContract(@NotNull Contract contractToUpdate);

    Optional<Contract> getContractById(@NotNull ObjectId contractId);

    List<Contract> getAllContractsOfCurrentAgent(@NotNull ObjectId currentAgentId);

    List<Contract> getAllContractsByListOfId(@NotNull List<ObjectId> contractsId);

    Optional<Contract> getContractByContractNumber(@NotNull String contractNumber);
}
