package jsoupDemo;

/**
 * Created by xdcao on 2017/7/7.
 */
public class RuleException extends RuntimeException {

    public RuleException() {
        super();
    }

    public RuleException(String message,Throwable cause) {
        super(message,cause);
    }

    public RuleException(String message){
        super(message);
    }

    public RuleException(Throwable cause){
        super(cause);
    }


}
