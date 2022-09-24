package space.xiami.project.genshindataviewer.service.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.service.util.PathUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * @author Xiami
 */
@Component
public abstract class AbstractSingleFileFactory<ExcelConfigData, GroupField> extends AbstractFileBaseFactory{

    /**
     * 相关的文件路径
     * @return 文件路径
     */
    public abstract String relatedFilePath();

    /**
     * 获取唯一主键
     * @param excelConfigData 分组对象
     * @return 唯一主键
     */
    public abstract GroupField getGroupValue(ExcelConfigData excelConfigData);

    public static final Logger log = LoggerFactory.getLogger(AbstractSingleFileFactory.class);

    private final Map<GroupField, ExcelConfigData> excelConfigDataMap = new HashMap<>();

    @Resource
    private TextMapFactory textMapFactory;

    @Override
    public Set<String> relatedFilePathSet() {
        return new HashSet<>(Collections.singletonList(relatedFilePath()));
    }

    @Override
    public void load(String path) {
        try{
            List<ExcelConfigData> array = (List<ExcelConfigData>) readJsonArray(
                    path,
                    (Class<ExcelConfigData>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]
            );
            for (ExcelConfigData data : array) {
                if(excelConfigDataMap.containsKey(getGroupValue(data))){
                    log.warn("Ignore same key={}", getGroupValue(data));
                    continue;
                }
                excelConfigDataMap.put(getGroupValue(data), data);
            }
        } catch (IOException e) {
            log.error("load error", e);
        }
    }

    @Override
    protected void clear(String path) {
        excelConfigDataMap.clear();
    }

    public ExcelConfigData get(GroupField key){
        if(excelConfigDataMap.containsKey(key)){
            return excelConfigDataMap.get(key);
        }
        return null;
    }
}
