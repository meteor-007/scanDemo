package cnvd.反编译;

import cnvd.反编译.DecriptClassLoader;

public class DemoLoad {

    /**
     * @param args
     */
    public static void main(String[] args) {
        DecriptClassLoader loader = new DecriptClassLoader("C:\\Users\\Administrator\\Desktop\\反编译.txt");
        try {
            Class<?> c = loader.loadClass("HelloWorld");
            System.out.println(c);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
