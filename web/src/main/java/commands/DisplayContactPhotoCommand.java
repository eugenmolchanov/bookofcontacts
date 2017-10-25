package commands;

import com.itechart.javalab.firstproject.entities.Photo;
import com.itechart.javalab.firstproject.services.PhotoService;
import com.itechart.javalab.firstproject.services.impl.PhotoServiceImpl;
import org.apache.log4j.Logger;
import resources.ConfigurationManager;
import resources.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Files;
import java.sql.SQLException;

/**
 * Created by Yauhen Malchanau on 28.09.2017.
 */
public class DisplayContactPhotoCommand implements ActionCommand {

    private static Logger logger = Logger.getLogger(DisplayContactPhotoCommand.class);
    private PhotoService service = PhotoServiceImpl.getInstance();
    private final String ACTIVE_PAGE = ConfigurationManager.getProperty("contact");
    private final String ERROR_PAGE = ConfigurationManager.getProperty("error");

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            Photo photo = service.findById(id);
            String path = photo.getPathToFile().concat(photo.getUuid());
            byte[] imageBytes = Files.readAllBytes(new File(path).toPath());
            resp.setContentType("image/jpeg");
            resp.setContentLength(imageBytes.length);
            resp.getOutputStream().write(imageBytes);
            return ACTIVE_PAGE;
        } catch (SQLException e) {
            req.setAttribute("warningMessage", MessageManager.getProperty("error"));
            return ERROR_PAGE;
        } catch (Exception e) {
            logger.error("Can't load photo. Exception.");
            logger.error(e.getMessage(), e);
            req.setAttribute("warningMessage", MessageManager.getProperty("error"));
            return ERROR_PAGE;
        }
    }
}
