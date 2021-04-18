package sk.stu.fei.uim.bp.application.backend.file.utils;


import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;

public class ContractFileAttachment extends FileAttachment
{
    @NotNull
    private int contractVersion;

    public ContractFileAttachment(ObjectId fileInDbId, String fileName, int contractVersion) {
        super(fileInDbId, fileName);
        this.contractVersion = contractVersion;
    }


    public int getContractVersion() {
        return contractVersion;
    }

    public void setContractVersion(int contractVersion) {
        this.contractVersion = contractVersion;
    }
}
