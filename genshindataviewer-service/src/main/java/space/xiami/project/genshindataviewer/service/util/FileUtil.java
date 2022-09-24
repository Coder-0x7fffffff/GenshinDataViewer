package space.xiami.project.genshindataviewer.service.util;

import java.io.*;

/**
 * @author Xiami
 */
public class FileUtil {

    public static boolean isExists(String path){
        return new File(path).exists();
    }

    public static byte[] readFileOnce(String path) throws IOException {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int len;
        while((len = fis.read(buffer)) > 0){
            outputStream.write(buffer, 0, len);
        }
        return outputStream.toByteArray();
    }
}
