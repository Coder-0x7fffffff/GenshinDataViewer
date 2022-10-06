package space.xiami.project.genshindataviewer.service.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.service.util.FileUtil;
import space.xiami.project.genshindataviewer.service.util.PathUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Xiami
 */
@Component
public class ReadableFactory extends AbstractFileBaseFactory{

    private Logger log = LoggerFactory.getLogger(ReadableFactory.class);

    private static final Set<String> relatedFilePathSet = new HashSet<>();

    private static final String readAbleDir = PathUtil.getReadableDirectory();

    private static final String prefix = "ART/UI/Readable/";

    private static final String suffix = ".txt";

    private final Map<String, String> readableMap = new HashMap<>();

    static {
        relatedFilePathSet.addAll(PathUtil.listFilePaths(readAbleDir));
    }

    @Override
    protected void load(String path) {
        try{
            String text = new String(FileUtil.readFile(path));
            readableMap.put(convertPath(path), text);
        } catch (IOException e) {
            log.error("ReadableFactory error.", e);
        }
    }

    @Override
    protected void clear(String path) {
        readableMap.remove(convertPath(path));
    }

    @Override
    public Set<String> relatedFilePathSet() {
        return relatedFilePathSet;
    }

    public String getText(String key){
        readLock();
        String result = readableMap.get(key);
        readUnlock();
        return result;
    }

    private String convertPath(String path){
        path = prefix + path.replace("\\", "/").substring(readAbleDir.length());
        return path.substring(0, path.length() - suffix.length());
    }
}
