package dto;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by Yauhen Malchanau on 05.10.2017.
 */
@Data
public class AttachmentDataDto {
    private String id;
    private String fileName;
    private String commentary;
    private Timestamp date;
    private String pathToFile;
    private String uuid;
    private long contactId;
}
