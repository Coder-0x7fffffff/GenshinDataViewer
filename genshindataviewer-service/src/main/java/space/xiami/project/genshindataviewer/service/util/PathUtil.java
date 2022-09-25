package space.xiami.project.genshindataviewer.service.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Xiami
 */
public class PathUtil {

    /**
     * 本地资源路径
     */
    private static final String resourceDirectory = "resources/";

    /**
     * 远程资源路径
     */
    private static final String remoteDirectory = "";

    /**
     * 数据资源目录
     */
    private static final String dataDirectory = "GenshinData/";
    public static final String textMap = "TextMap/";
    public static final String excelBinOutput = "ExcelBinOutput/";
    public static final String readable = "Readable/";

    public static String getExcelBinOutputDirectory(){
        return getLocalDataDirectory() + dataDirectory + excelBinOutput;
    }

    public static String getReadableDirectory(){
        return getLocalDataDirectory() + dataDirectory + readable;
    }

    public static String getTextMapDirectory(){
        return getLocalDataDirectory() + dataDirectory + textMap;
    }

    /**
     * 获取本地资源路径
     * @return 本地资源路径
     */
    public static String getLocalDataDirectory(){
        return resourceDirectory;
    }

    public static String getRemoteDataDirectory(){
        return remoteDirectory;
    }

    /**
     * 转换数据本地路径到远程路径
     * @param path 本地路径
     * @return 远程路径
     */
    public static String localPath2RemotePath(String path){
        return getRemoteDataDirectory() + localPath2ResourcePath(path);
    }

    /**
     * 转远程路径到换数据本地路径
     * @param path 远程路径
     * @return 本地路径
     */
    public static String remotePath2LocalPath(String path){
        return getLocalDataDirectory() + remotePath2ResourcePath(path);
    }

    /**
     * 本地目录到资源相关路径
     * @param path 本地路径
     * @return 资源路径
     */
    public static String localPath2ResourcePath(String path){
        return path.substring(getLocalDataDirectory().length());
    }

    /**
     * 远程路径到资源相关路径
     * @param path 远程路径
     * @return 资源路径
     */
    public static String remotePath2ResourcePath(String path){
        return path.substring(getRemoteDataDirectory().length());
    }

    public static List<String> listFilePaths(String path){
        File dir = new File(path);
        if(!dir.exists()){
            return new ArrayList<>();
        }
        if(dir.isDirectory()){
            File[] files = dir.listFiles();
            if(files!=null){
                List<String> paths = new ArrayList<>(files.length);
                for(File file : files){
                    paths.add(file.getPath());
                }
                return paths;
            }
            return new ArrayList<>();
        }
        return Collections.singletonList(path);
    }
}
