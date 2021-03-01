package sk.stu.fei.uim.bp.application.backend.file;

import com.vaadin.flow.component.upload.receivers.FileData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.InputStream;

@Data
@AllArgsConstructor
public class FileWrapper
{
    private InputStream file;
    private FileData fileData;
    private String customFileName;

    public FileWrapper(InputStream file,FileData fileData)
    {
        this.setFile(file);
        this.setFileData(fileData);
    }

    public boolean isSetCustomFileName()
    {
        return this.customFileName != null && !this.customFileName.equals("");
    }


}
