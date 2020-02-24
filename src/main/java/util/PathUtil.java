package util;

public class PathUtil {
    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if(os.toLowerCase().startsWith("win")){
            //basePath = "E:\\apache-tomcat-8.5.39\\webapps\\Users\\baidu\\work\\image";
            basePath = "C:\\Users\\19429\\Desktop\\Users\\baidu\\work\\image\\upload\\images\\item\\shop\\1";
        }else {
            basePath="";
        }
        return basePath;
    }
    public static String getHospitalImagePath(long shopId){
        String imagePath = "C:\\Users\\19429\\Desktop\\Users\\baidu\\work\\image\\upload\\images\\item\\shop\\1";
        return imagePath;
    }
}
