package sispatri.controller;

import org.apache.log4j.Logger;

/**
 * Classe responsável por gerar os logs da aplicação.
 * 
 * @author augusto
 */
public class LoggerController {

    public static void log(Class pClass, Exception pException) {
        Logger logger = Logger.getLogger(pClass);
        logger.error(pException);
    }
}
