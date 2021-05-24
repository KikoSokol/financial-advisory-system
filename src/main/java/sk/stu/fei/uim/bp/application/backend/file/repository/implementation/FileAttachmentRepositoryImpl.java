package sk.stu.fei.uim.bp.application.backend.file.repository.implementation;

import com.mongodb.client.result.DeleteResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.ContractDocument;
import sk.stu.fei.uim.bp.application.backend.file.repository.FileAttachmentRepository;
import sk.stu.fei.uim.bp.application.backend.file.repository.FileRepository;
import sk.stu.fei.uim.bp.application.backend.file.utils.FileAttachment;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public class FileAttachmentRepositoryImpl implements FileAttachmentRepository
{
    private final MongoOperations mongoOperations;
    private final FileRepository fileRepository;

    @Autowired
    public FileAttachmentRepositoryImpl(MongoTemplate mongoOperations, FileRepositoryImpl fileRepository)
    {
        this.mongoOperations = mongoOperations;
        this.fileRepository = fileRepository;
    }

    @Override
    public FileAttachment addNewFileAttachment(@NotNull FileAttachment newFileAttachment)
    {
        return this.mongoOperations.insert(newFileAttachment);
    }

    @Override
    public FileAttachment getFileAttachmentById(@NotNull ObjectId fileAttachmentId)
    {
        return this.mongoOperations.findById(fileAttachmentId,FileAttachment.class);
    }

    @Override
    public List<FileAttachment> getFileAttachmentByListOfId(@NotNull List<ObjectId> fileAttachmentIds)
    {
        Criteria criteria = new Criteria("fileAttachmentId");
        criteria.in(fileAttachmentIds);

        Query query = new Query(criteria);

        return this.mongoOperations.find(query, FileAttachment.class);
    }

    @Override
    public boolean deleteAllFileAttachmentsByList(List<FileAttachment> fileAttachmentsToDelete)
    {
        for(FileAttachment fileAttachment : fileAttachmentsToDelete)
        {
            this.mongoOperations.remove(fileAttachment);
            this.fileRepository.deleteFile(fileAttachment.getFileInDbId());
        }

        return true;
    }

}
