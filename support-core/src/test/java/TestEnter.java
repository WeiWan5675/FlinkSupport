import com.weiwan.support.core.SupportAppEnter;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/3 13:48
 * @Package: PACKAGE_NAME.TestEnter
 * @ClassName: TestEnter
 * @Description:
 **/
public class TestEnter {

    private String[] args;

    public void before() {
        ArrayList arg = new ArrayList();
        arg.add("-jc");
        arg.add("");
    }


    @Test
    public void testEnterMain() throws Exception {
        SupportAppEnter.main(args);
    }


}
