package util;

import java.util.logging.Logger;

public class ExitSaver implements Runnable{
    final Logger logger = Logger.getLogger(ExitSaver.class.getCanonicalName());

    @Override
    public void run() {
        logger.info("Server was turned off!");
    }
}
