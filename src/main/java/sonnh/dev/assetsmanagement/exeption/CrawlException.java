package sonnh.dev.assetsmanagement.exeption;

public class CrawlException extends RuntimeException {

    public CrawlException(String message, Throwable cause) {
        super(message, cause);
    }
}