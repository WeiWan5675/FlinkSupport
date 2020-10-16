import com.weiwan.support.common.utils.VariableCheckTool;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @Author: xiaozhennan
 * @Date: 2020/10/16 11:42
 * @Package: PACKAGE_NAME.TestCheckUtil
 * @ClassName: TestCheckUtil
 * @Description:
 **/
public class TestCheckUtil {


    @Test
    public void testCheckUtil() {

        if (VariableCheckTool.checkNullSupportExecption(null)) {
            System.out.println("不为空");
        }
    }


}
