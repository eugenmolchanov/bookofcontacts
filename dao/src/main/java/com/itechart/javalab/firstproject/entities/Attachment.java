package com.itechart.javalab.firstproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Objects;

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
            return Objects.equals(this.fileName, attachment.getFileName()) && Objects.equals(this.commentary, attachment.commentary) &&
                    Objects.equals(this.date, attachment.getDate()) && Objects.equals(this.pathToFile, attachment.pathToFile);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Objects.hashCode(this.fileName);
        result = 31 * result + Objects.hashCode(this.commentary);
        result = 31 * result + Objects.hashCode(this.date);
        result = 31 * result + Objects.hashCode(this.pathToFile);
        return result;
    }
}
