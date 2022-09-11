package space.xiami.project.genshindataviewer.client.util;

import java.io.*;

public class FileUtil {
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
