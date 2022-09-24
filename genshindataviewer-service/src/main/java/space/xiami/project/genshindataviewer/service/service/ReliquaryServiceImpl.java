package space.xiami.project.genshindataviewer.service.service;

import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.client.ReliquaryService;
import space.xiami.project.genshindataviewer.domain.ResultDO;
import space.xiami.project.genshindataviewer.domain.model.Reliquary;
import space.xiami.project.genshindataviewer.domain.model.ReliquarySet;
import space.xiami.project.genshindataviewer.service.manager.ReliquaryManager;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Xiami
 */
@Component
public class ReliquaryServiceImpl implements ReliquaryService {

    @Resource
    private ReliquaryManager reliquaryManager;

    @Override
    public ResultDO<Map<String, List<Long>>> getReliquaryIds(Byte lang){
        Map<String, List<Long>> ret = reliquaryManager.getReliquaryIdsMap(lang);
        if(ret == null){
            return ResultDO.buildErrorResult("查询失败");
        }
        return ResultDO.buildSuccessResult(ret);
    }

    @Override
    public ResultDO<List<Long>> getReliquaryId(Byte lang, String name){
        List<Long> ret = reliquaryManager.getReliquaryIds(lang, name);
        if(ret == null){
            return ResultDO.buildErrorResult("查询失败");
        }
        return ResultDO.buildSuccessResult(ret);
    }

    @Override
    public ResultDO<List<Reliquary>> getReliquaries(Byte lang, String name){
        List<Reliquary> ret = reliquaryManager.getReliquaries(lang, name);
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
    public ResultDO<ReliquarySet> getReliquarySet(Byte lang, Long id) {
        ReliquarySet ret = reliquaryManager.getReliquarySet(lang, id);
        if(ret == null){
            return ResultDO.buildErrorResult("查询失败");
        }
        return ResultDO.buildSuccessResult(ret);
    }
}
