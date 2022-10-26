package space.xiami.project.genshindataviewer.service.service;

import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.client.AvatarService;
import space.xiami.project.genshindataviewer.domain.ResultDO;
import space.xiami.project.genshindataviewer.domain.model.Avatar;
import space.xiami.project.genshindataviewer.domain.model.TeamResonance;
import space.xiami.project.genshindataviewer.domain.model.TeamResonanceGroup;
import space.xiami.project.genshindataviewer.service.manager.AvatarManager;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Xiami
 */
@Component
public class AvatarServiceImpl implements AvatarService {

    @Resource
    private AvatarManager avatarManager;

    @Override
    public ResultDO<Map<String, Long>> getAvatarIds(Byte lang){
        Map<String, Long> ret = avatarManager.getAvatarIds(lang);
        if(ret == null){
            return ResultDO.buildErrorResult("查询失败");
        }
        return ResultDO.buildSuccessResult(ret);
    }

    @Override
    public ResultDO<Long> getAvatarId(Byte lang, String name){
        Long ret = avatarManager.getAvatarId(lang, name);
        if(ret == null){
            return ResultDO.buildErrorResult("查询失败");
        }
        return ResultDO.buildSuccessResult(ret);
    }

    @Override
    public ResultDO<Avatar> getAvatar(Byte lang, String name, Byte elementalType){
        Avatar ret = avatarManager.getAvatar(lang, name, elementalType);
        if(ret == null){
            return ResultDO.buildErrorResult("查询失败");
        }
        return ResultDO.buildSuccessResult(ret);
    }

    @Override
    public ResultDO<Avatar> getAvatar(Byte lang, Long id, Byte elementalType){
        Avatar ret = avatarManager.getAvatar(lang, id, elementalType);
        if(ret == null){
            return ResultDO.buildErrorResult("查询失败");
        }
        return ResultDO.buildSuccessResult(ret);
    }

    @Override
    public ResultDO<TeamResonance> getTeamResonance(Byte lang, Long id) {
        TeamResonance ret = avatarManager.getTeamResonance(lang, id);
        if(ret == null){
            return ResultDO.buildErrorResult("查询失败");
        }
        return ResultDO.buildSuccessResult(ret);
    }

    @Override
    public ResultDO<TeamResonanceGroup> getTeamResonanceGroup(Byte lang, List<Long> ids, List<Byte> appointElementalTypes){
        TeamResonanceGroup ret = avatarManager.getTeamResonanceGroup(lang, ids, appointElementalTypes);
        if(ret == null){
            return ResultDO.buildErrorResult("查询失败");
        }
        return ResultDO.buildSuccessResult(ret);
    }
}
