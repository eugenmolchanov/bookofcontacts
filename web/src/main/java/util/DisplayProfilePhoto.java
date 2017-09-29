package util;


import com.itechart.javalab.firstproject.entities.Contact;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by Yauhen Malchanau on 28.09.2017.
 */
public class DisplayProfilePhoto {

    public static byte[] execute(HttpServletRequest req, HttpServletResponse resp, Contact contact) throws IOException {
        String path = contact.getPhoto().getPathToFile().concat(contact.getPhoto().getUuid());
        return Files.readAllBytes(new File(path).toPath());
    }
}
