package com.itechart.javalab.firstproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * Created by Евгений Молчанов on 06.09.2017.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {
    private long id;
    private String fileName;
    private String commentary;
    private Date date;
    private String pathToFile;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Attachment) {
            Attachment attachment = (Attachment) obj;
            return this.fileName.equals(attachment.getFileName()) && this.commentary.equals(attachment.getCommentary()) && this.date.getTime() ==
                    attachment.getDate().getTime() && this.pathToFile.equals(attachment.getPathToFile());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (fileName != null) {result = 31 * result + fileName.hashCode();}
        if (commentary != null) {result = 31 * result + commentary.hashCode();}
        if (date != null) {result = 31 * result + date.hashCode();}
        if (pathToFile != null) {result = 31 * result + pathToFile.hashCode();}
        return result;
    }
}
