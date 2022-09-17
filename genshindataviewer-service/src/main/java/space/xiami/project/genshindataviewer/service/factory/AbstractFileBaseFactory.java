package space.xiami.project.genshindataviewer.service.factory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import space.xiami.project.genshindataviewer.service.util.FileUtil;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractFileBaseFactory implements FileBaseFactory {

    private Logger log = LoggerFactory.getLogger(AbstractFileBaseFactory.class);

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
        // 读取数据
        String jsonString = new String(FileUtil.readFileOnce(filePath));
        // 对比输入对象的字段数据
        JSONArray jsonArray = JSON.parseArray(jsonString);
        Set<String> jsonFieldSet = new HashSet<>();
        for(Object object : jsonArray){
            if(object instanceof JSONObject){
                JSONObject jsonObject = (JSONObject) object;
                jsonFieldSet.addAll(jsonObject.keySet());
            }
        }
        Set<String> clazzFieldSet = Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).collect(Collectors.toSet());
        List<String> missedJsonFields = new ArrayList<>(jsonFieldSet);
        List<String> missedClazzFields = new ArrayList<>(clazzFieldSet);
        missedJsonFields.removeAll(clazzFieldSet);
        missedClazzFields.removeAll(jsonFieldSet);
        if(missedJsonFields.size() > 0){
            log.warn("{} missed field, field: {}", clazz.getSimpleName(), missedJsonFields);
        }
        if(missedClazzFields.size() > 0){
            log.warn("{} read fields which not exists, field: {}", clazz.getSimpleName(), missedClazzFields);
        }

        // 获取json数据
        return JSON.parseArray(jsonString, clazz);
    }
}
