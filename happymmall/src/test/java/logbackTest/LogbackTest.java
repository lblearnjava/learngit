package logbackTest;

/**
 * Created by lanbingdepingguo on 2017/7/23.
 */
import  org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackTest {

    private final static Logger logger = LoggerFactory.getLogger(LogbackTest.class);

    public static void main(String[] args) {



              logger.error("你麻痹，错了");
              logger.info("射你妈一脸");


    }
}
