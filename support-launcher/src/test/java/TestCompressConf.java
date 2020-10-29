import com.weiwan.support.common.utils.FileUtil;
import com.weiwan.support.common.utils.StringCompressUtil;

import java.io.IOException;

/**
 * @Author: xiaozhennan
 * @Date: 2020/10/27 10:58
 * @Package: PACKAGE_NAME.TestCompressConf
 * @ClassName: TestCompressConf
 * @Description:
 **/
public class TestCompressConf {
    public static void main(String[] args) throws IOException {
        String s = FileUtil.readFileContent("F:\\Project\\FlinkSupport\\target\\FlinkSupport-1.0\\conf\\support-core.yaml");

        String compress = StringCompressUtil.compress(s);
        String uncompress = StringCompressUtil.uncompress(compress);
        System.out.println(compress);
        System.out.println(uncompress);
        System.out.println(s);
    }
}
