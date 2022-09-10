package space.xiami.project.genshindataviewer.client.service;

import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.client.util.EnumUtils;
import space.xiami.project.genshindataviewer.client.util.TextMapUtil;
import space.xiami.project.genshindataviewer.domain.ResultDO;

import java.util.Map;

@Component
public class BaseDataService {

    public ResultDO<String> getTextByLangId(Byte lang, Long id){
        String err = TextMapUtil.errorMsg(lang, id);
        if(err != null){
            return ResultDO.buildErrorResult(err);
        }
        return ResultDO.buildSuccessResult(TextMapUtil.getText(lang, id));
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
