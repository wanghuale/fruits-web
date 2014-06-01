import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;


/**
 * 版权：融贯资讯 <br/>
 * 作者：xuan.zhou@rogrand.com <br/>
 * 生成日期：2014-3-31 <br/>
 * 描述：〈描述〉
 */
public class Test {

    public static void main(String[] args) throws ParseException {
        String datetime = "2014-03-28 16:30:48";
        Date date = DateUtils.parseDate(datetime, new String[]{"yyyy-MM-dd HH:mm:ss"});
        System.out.println(date.getTime());
        
        System.out.println(System.getProperty("user.home"));
    }
}
