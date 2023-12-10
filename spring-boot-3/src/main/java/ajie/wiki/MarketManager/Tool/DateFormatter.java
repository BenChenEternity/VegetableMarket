package ajie.wiki.MarketManager.Tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Component
public class DateFormatter implements CommandLineRunner {
    @Value("${global.timezone:}")
    private String timezone;
    private static ZoneId zoneId;

    private static final Logger logger = LoggerFactory.getLogger(DateFormatter.class);

    @Override
    public void run(String... args) {
        try {
            if (Objects.nonNull(timezone)) {
                zoneId = ZoneId.of(timezone);
                logger.debug("使用时区："+timezone);
            } else {
                logger.debug("未设置时区，使用系统时区。");
                zoneId = ZoneId.systemDefault();
            }
        }catch (Exception e){
            logger.debug("不正确的时区： "+timezone+" ，使用系统时区。");
            zoneId = ZoneId.systemDefault();
        }
    }

    public static LocalDate format(Date date) {
        return (Objects.nonNull(date)) ? date.toInstant().atZone(zoneId).toLocalDate() : null;
    }
}
