package space.xiami.project.genshindataviewer.client.service;

import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.client.manager.WeaponManager;
import space.xiami.project.genshindataviewer.domain.ResultDO;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class WeaponService {

    @Resource
    private WeaponManager weaponManager;

    public ResultDO<Map<String, Long>> getWeaponIds(Byte lang){
        Map<String, Long> ret = weaponManager.getWeaponIds(lang);
        if(ret == null){
            return ResultDO.buildErrorResult("查询失败");
        }
        return ResultDO.buildSuccessResult(ret);
    }
}
