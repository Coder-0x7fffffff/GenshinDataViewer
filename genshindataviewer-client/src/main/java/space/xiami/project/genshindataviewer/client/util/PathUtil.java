package space.xiami.project.genshindataviewer.client.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PathUtil {

    private static final String resourceDirectory = "resources/";
    private static final String dataDirectory = "GenshinData/";

    public static final String textMap = "TextMap/";
    public static final String excelBinOutput = "ExcelBinOutput/";

    public static String getExcelBinOutputDirectory(){
        return resourceDirectory + dataDirectory + excelBinOutput;
    }

    public static String getTextMapDirectory(){
        return resourceDirectory + dataDirectory + textMap;
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
