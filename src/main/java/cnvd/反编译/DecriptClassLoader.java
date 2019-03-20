package cnvd.反编译;


import java.io.FileOutputStream;

/**
 * 解密的类加载器
 *
 * @author zml
 * @date 2018-7-3
 */
public class DecriptClassLoader extends ClassLoader {

    private String rootDir;

    public DecriptClassLoader(String rootDir) {
        this.rootDir = rootDir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> c = findLoadedClass(name);
        if (c != null) {
            return c;
        } else {
            ClassLoader parent = this.getParent();
            try {
                c = parent.loadClass(name);
            } catch (Exception e) {
                //e.printStackTrace();
            }    //双亲委托机制，委派给父类加载。
            if (c != null) {
                return c;
            } else {
//                byte[] classData = getClassData(name);
//                if (classData == null) {
//                    throw new ClassNotFoundException();
//                } else {
//                    try {
//                        FileOutputStream fos = new FileOutputStream("D:\\kk.class");
//                        fos.write(classData);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    if(name.equals("HelloWorld")){
//                        name="com.ankki.cfcmms.decomplie.HelloWorld";
//                    }
//                    c = defineClass(name, classData, 0, classData.length);
//                }
            }
        }
        return c;
    }

//    private byte[] getClassData(String name) {
//        String path = rootDir + "/" + name.replace('.', '/') + ".class";
////        String path = rootDir + ".class";
//        return ClassDecrypt.decrypt(path);
//    }
}
