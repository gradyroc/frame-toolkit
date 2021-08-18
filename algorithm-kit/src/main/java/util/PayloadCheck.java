package util;

/**
 * @author grady
 * @version 1.0, on 20:19 2021/7/25.
 */
public class PayloadCheck {

    public static boolean arrHeaderCheck(int [] arr){
        if (arr ==null || arr.length<2){
            return true;
        }
        return false;
    }
}
