package commands;

import com.itechart.javalab.firstproject.entities.Photo;
import com.itechart.javalab.firstproject.services.PhotoService;
import com.itechart.javalab.firstproject.services.impl.PhotoServiceImpl;
import org.apache.log4j.Logger;
import resources.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

/**
 * Created by Yauhen Malchanau on 28.09.2017.
 */
public class DisplayContactPhoto implements ActionCommand {

    private static Logger logger = Logger.getLogger(DisplayContactPhoto.class);
    private PhotoService service = PhotoServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        long id = Long.parseLong(req.getParameter("id"));
        try {
            Photo photo = service.findById(id);
            String path = photo.getPathToFile().concat(photo.getUuid());
            byte[] imageBytes = Files.readAllBytes(new File(path).toPath());

            resp.setContentType("image/jpeg");
            resp.setContentLength(imageBytes.length);
            resp.getOutputStream().write(imageBytes);
        } catch (SQLException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
        return ConfigurationManager.getProperty("contact");
    }
}
