package file;

import java.io.File;

/**
 * 文件分割测试
 * @Auther: czwei
 * @Date: 2019/3/2 10:51
 */
public class TestFileSlipt {

    public static void main(String[] args) throws Exception {
        //分割文件
        FileSliptUtil.splitFileDemo(new File("D:\\数据库漏洞扫描\\数据库漏洞扫描\\密码字典\\4位\\password-4.txt"), 10);
        //合并文件
        //FileSliptUtil.joinFileDemo(new String[]{"C://Linux//jdk//jdk-7u76-linux-x64_data1.gz", "C://Linux//jdk-7u76-linux-x64_data2.gz", "C://Linux//jdk-7u76-linux-x64_data3.gz"});
    }
}
