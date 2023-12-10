package ajie.wiki.MarketManager.ExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import redis.clients.jedis.exceptions.JedisConnectionException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    //请求体错误
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        logger.info("用户发送了无效的请求体");
        logger.debug(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();//不用BAD_REQUEST，没有权限和错误请求一律404，防止攻击者轻易猜出POST的url
    }

    //redis服务没开（连接失败）
    @ExceptionHandler(JedisConnectionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleJedisConnectionException(JedisConnectionException ex) {
        logger.error("连接Redis服务器失败");
        logger.debug(ex.getMessage());
    }
}
