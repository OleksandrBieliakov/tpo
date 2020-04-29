package Listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener
public class RequestListener implements ServletRequestListener, ServletContextListener {
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        Logger logger = Logger.getLogger("REQUEST LISTENER");
        logger.setLevel(Level.SEVERE);
        logger.severe("REQ DEST: " + sre.toString());
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        Logger logger = Logger.getLogger("REQUEST LISTENER");
        logger.setLevel(Level.SEVERE);
        logger.severe("REQ INIT: " + sre.toString());
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Logger logger = Logger.getLogger("CONTEXT LISTENER");
        logger.setLevel(Level.SEVERE);
        logger.severe("CONTEXT INIT: " + sce.toString());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Logger logger = Logger.getLogger("CONTEXT LISTENER");
        logger.setLevel(Level.SEVERE);
        logger.severe("CONTEXT DEST: " + sce.toString());
    }
}

