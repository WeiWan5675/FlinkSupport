import com.weiwan.support.utils.cluster.ClusterConfigLoader;
import org.apache.flink.configuration.ConfigOption;
import org.apache.hadoop.conf.Configuration;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @Author: xiaozhennan
 * @Date: 2020/10/27 13:50
 * @Package: PACKAGE_NAME.TestConfiguration
 * @ClassName: TestConfiguration
 * @Description:
 **/
public class TestConfiguration {
    public static void main(String[] args) {

        Configuration configuration = ClusterConfigLoader.loadHadoopConfig("F:\\hadoop-2.7.1\\etc\\hadoop");

        Iterator<Map.Entry<String, String>> iterator = configuration.iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            System.out.println(next);
        }

    }
}
