package space.xiami.project.genshindataviewer.service.service;

import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.client.ReliquaryService;
import space.xiami.project.genshindataviewer.domain.ResultDO;
import space.xiami.project.genshindataviewer.domain.model.Reliquary;
import space.xiami.project.genshindataviewer.domain.model.ReliquarySet;
import space.xiami.project.genshindataviewer.service.manager.ReliquaryManager;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Xiami
 */
@Component
public class ReliquaryServiceImpl implements ReliquaryService {

    @Resource
    private ReliquaryManager reliquaryManager;

    @Override
    public ResultDO<Map<String, Long>> getReliquarySetId(Byte lang) {
        Map<String, Long> ret = reliquaryManager.getReliquarySetIdMap(lang);
        if(ret == null){
            return ResultDO.buildErrorResult("查询失败");
        }
        return ResultDO.buildSuccessResult(ret);
    }

    @Override
    public ResultDO<Long> getReliquarySetId(Byte lang, String name) {
        Long ret = reliquaryManager.getReliquarySetId(lang, name);
        if(ret == null){
            return ResultDO.buildErrorResult("查询失败");
        }
        return ResultDO.buildSuccessResult(ret);
    }

    @Override
    public ResultDO<Map<String, Long>> getReliquaryId(Byte lang){
        Map<String, Long> ret = reliquaryManager.getReliquaryIdMap(lang);
        if(ret == null){
            return ResultDO.buildErrorResult("查询失败");
        }
        return ResultDO.buildSuccessResult(ret);
    }

    @Override
    public ResultDO<Long> getReliquaryId(Byte lang, String name){
        Long ret = reliquaryManager.getReliquaryId(lang, name);
        if(ret == null){
            return ResultDO.buildErrorResult("查询失败");
        }
        return ResultDO.buildSuccessResult(ret);
    }

    @Override
    public ResultDO<Reliquary> getReliquary(Byte lang, String name){
        Reliquary ret = reliquaryManager.getReliquary(lang, name);
        if(ret == null){
            return ResultDO.buildErrorResult("查询失败");
        }
        return ResultDO.buildSuccessResult(ret);
    }

    @Override
    public ResultDO<Reliquary> getReliquary(Byte lang, Long id){
        Reliquary ret = reliquaryManager.getReliquary(lang, id);
        if(ret == null){
            return ResultDO.buildErrorResult("查询失败");
        }
        return ResultDO.buildSuccessResult(ret);
    }

    @Override
    public ResultDO<ReliquarySet> getReliquarySet(Byte lang, String name) {
        ReliquarySet ret = reliquaryManager.getReliquarySet(lang, name);
        if(ret == null){
            return ResultDO.buildErrorResult("查询失败");
        }
        return ResultDO.buildSuccessResult(ret);
    }

    @Override
    public ResultDO<ReliquarySet> getReliquarySet(Byte lang, Long id) {
        ReliquarySet ret = reliquaryManager.getReliquarySet(lang, id);
        if(ret == null){
            return ResultDO.buildErrorResult("查询失败");
        }
        return ResultDO.buildSuccessResult(ret);
    }
}
