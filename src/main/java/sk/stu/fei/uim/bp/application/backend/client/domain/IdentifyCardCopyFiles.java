package sk.stu.fei.uim.bp.application.backend.client.domain;

import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.Data;

@Data
public class IdentifyCardCopyFiles
{
    private GridFSFile frontSide;
    private GridFSFile backSide;

}
