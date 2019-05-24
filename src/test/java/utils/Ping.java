package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @Auther: czwei
 * @Date: 2019/3/26 19:53
 */
public class Ping {


    public static boolean ping(String ipAddress, int pingTimes, int timeOut) {
        //获取操作系统类型
        String osName = System.getProperty("os.name");
        String pingCommand = "";
        if (osName.toLowerCase().contains("linux")) {
            pingCommand = "ping -c " + pingTimes + " -i " + timeOut + " " + ipAddress;
        } else {
            pingCommand = "ping " + ipAddress + " -n " + pingTimes + " -w " + timeOut;
        }
        BufferedReader in = null;
        Runtime r = Runtime.getRuntime();
        try {
            Process p = r.exec(pingCommand);
            if (p == null) {
                return false;
            }
            // 逐行检查输出,计算类似出现=23ms TTL=62字样的次数
            in = new BufferedReader(new InputStreamReader(p.getInputStream(), "GB2312"));
            int connectedCount = 0;
            String line;
            while ((line = in.readLine()) != null) {
                connectedCount += getCheckResult(line);
            }
            // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真
            return connectedCount == pingTimes;
        } catch (Exception ex) {
            // 出现异常则返回假
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.
    private static int getCheckResult(String line) {
         System.out.println("控制台输出的结果为:"+line);
        Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            return 1;
        }
        return 0;
    }

    public static void main(String[] args) throws Exception {
        String ipAddress = "172.19.1.28";
        System.out.println(ping(ipAddress, 5, 5000));
    }
}
