package space.xiami.project.genshindataviewer.client.factory;

import com.alibaba.fastjson.JSON;
import space.xiami.project.genshindataviewer.client.util.FileUtil;

import java.io.IOException;
import java.util.List;

public abstract class AbstractFileBaseFactory implements FileBaseFactory {

    protected abstract void load(String path);

    protected abstract void clear(String path);

    @Override
    public void reload(String path){
        if(relatedFilePathSet().contains(path)){
            clear(path);
            load(path);
        }
    }

    public <T> List<T> readJsonArray(String filePath, Class<T> clazz) throws IOException {
        return JSON.parseArray(new String(FileUtil.readFileOnce(filePath)), clazz);
    }
}
