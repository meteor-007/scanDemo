//package upgrade;
//
//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
//
//import java.io.File;
//
///**
// * @description:
// * @author: czwei
// * @create: 2019-01-24 16:02
// */
//public class UnzipService {
//
//    //创建解析器
//    MultipartHttpServletRequest mh=(MultipartHttpServletRequest) request;
//    //获得文件
//    CommonsMultipartFile cmf=(CommonsMultipartFile) mh.getFile("hostFileBatch");
//    //获得原始文件名
//    String oriName=cmf.getOriginalFilename();
//    //拼接年+月
//    String path=hostYear.concat(quarter);
//    //		String path=String.valueOf(year).concat(String.valueOf(month));
//    String savePath= PolicyUtils.Commons.HOST_UPLOAD_PATH+"\\"+path; //保存的路径
////		File storeDirectory=new File(savePath);
//    //判断是不是zip文件
//		if(oriName.matches(".*.zip")) {
//        //判断文件目录是否存在
//        File file=new File(savePath);
//        //不存在
//        if(!file.exists()) {
//            //创建文件夹
//            file.mkdirs();
//        }else {
//            //文件存在则删除
//            deleteDir(file);
////				file.delete();
//            file.mkdirs();
//            //删除文件原始路径
//            int deletePath=uploadZipDao.deleteOldFile(oriName,savePath);
//        }
//        //获取文件
//        FileItem fileItem=cmf.getFileItem();
//        try {
//            //解压文件到指定目录
//            FileUnZip.zipToFile(oriName, savePath);
//        } catch (Exception e){
//            e.printStackTrace();
//            new File(savePath,oriName).delete();
//        }
//        fileItem.delete();
//        //保存路径文件名
//        boolean result=addPath(oriName,savePath);
//        if(result==true) {
//            //执行批处理
//            return true;
//        }
//        return false;
//    }else {
//        //不是zip则返回false
//        return false;
//    }
//}
//
//}
