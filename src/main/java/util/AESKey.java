package util;

/**
 * @author czjuan
 * @date 2018/4/9
 */
public enum AESKey {

    /**
     * 给每种类型定义不同的密钥
     * 注：以下的key都是固定的，还有key的长度必须为16个字符
     */

    //数据库配置密码加解密密钥
    DBCONFIG("ANKKIAESDBCONFIG"),

    //验证码加密密钥
    RANDCODE("ANKKIAESRANDCODE"),

    //登入密码加密密钥
    LOGIN("ANKKIUSEAESLOGIN"),

    //邮件密码加密密钥
    MAIL("ANKKIAMAILPASSWD");


    private String key;

    private AESKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
