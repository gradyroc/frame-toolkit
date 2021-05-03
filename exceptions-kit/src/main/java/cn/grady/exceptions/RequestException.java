package cn.grady.exceptions;

import cn.grady.interfaces.BasicEnum;

/**
 * @author rociss
 * @version 1.0, on 22:31 2020/4/1.
 */
public class RequestException extends RuntimeException{

    private static final long serialVersionUID = 8093596867786502598L;

    /*
    错误码，int 或者 String
     */
    private String code;

    public RequestException(){
        super();
    }

    public RequestException(Throwable cause){
        super(cause);
    }
    public RequestException(String code,Throwable cause){
        super(cause);
        this.code = code;

    }

    public RequestException(String code,String message,Throwable cause){
        super(message,cause);
        this.code = code;
    }


    /*
    增加兼容enum 统一处理异常的构造函数
     */
    public   RequestException(BasicEnum<String > errorEnum,String ... param){
        super(String.format(errorEnum.getDescription(),param));
        this.code = errorEnum.getValue();

    }

    public   RequestException(BasicEnum<String > errorEnum,Throwable cause,String ... param){
        super(String.format(errorEnum.getDescription(),param),cause);
        this.code = errorEnum.getValue();

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
