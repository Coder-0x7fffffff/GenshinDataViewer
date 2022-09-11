package space.xiami.project.genshindataviewer.client.service;

import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.client.util.EnumUtils;
import space.xiami.project.genshindataviewer.client.factory.TextMapFactory;
import space.xiami.project.genshindataviewer.domain.ResultDO;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class BaseDataService {

    @Resource
    private TextMapFactory textMapFactory;

    public ResultDO<String> getTextByLangId(Byte lang, Long id){
        String err = textMapFactory.checkError(lang, id);
        if(err != null){
            return ResultDO.buildErrorResult(err);
        }
        return ResultDO.buildSuccessResult(textMapFactory.getText(lang, id));
    }

    public ResultDO<Map<Byte, String>> getValueDescOfEnum(String name){
        Map<Byte, String> ret = EnumUtils.getEnum(name);
        if(ret == null){
            return ResultDO.buildErrorResult("获取失败");
        }
        return ResultDO.buildSuccessResult(ret);
    }

    public ResultDO<Map<String, Map<Byte, String>>> getValueDescOfAllEnums(){
        Map<String, Map<Byte, String>> ret = EnumUtils.getAllEnum();
        if(ret == null){
            return ResultDO.buildErrorResult("获取失败");
        }
        return ResultDO.buildSuccessResult(ret);
    }
}
