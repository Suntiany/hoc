package util;

public class PathUtil {
    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if(os.toLowerCase().startsWith("win")){
            //basePath = "E:\\apache-tomcat-8.5.39\\webapps\\Users\\baidu\\work\\image";
            basePath = "E:\\apache-tomcat-8.5.39\\webapps\\Users\\baidu\\work\\image";
        }else {
            basePath="";
        }
        return basePath;
    }
    public static String getHospitalImagePath(long hospitalId){
        String imagePath = "/upload/images/item/hospital/"+hospitalId+"/";
        return imagePath;
    }
}
