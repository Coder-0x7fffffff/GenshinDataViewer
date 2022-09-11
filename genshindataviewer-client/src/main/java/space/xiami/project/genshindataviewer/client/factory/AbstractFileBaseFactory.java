package space.xiami.project.genshindataviewer.client.factory;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.InitializingBean;
import space.xiami.project.genshindataviewer.client.util.FileUtil;

import java.io.*;
import java.util.*;

public abstract class AbstractFileBaseFactory implements InitializingBean, FileBaseFactory {

    protected abstract void load(String path);

    @Override
    public void afterPropertiesSet() throws Exception {
        Set<String> paths = relatedFilePathSet();
        for(String path : paths){
            load(path);
        }
    }

    @Override
    public void reload(String path){
        if(relatedFilePathSet().contains(path)){
            load(path);
        }
    }

    public <T> List<T> readJsonArray(String filePath, Class<T> clazz) throws IOException {
        return JSON.parseArray(new String(FileUtil.readFileOnce(filePath)), clazz);
    }
}
