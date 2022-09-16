package space.xiami.project.genshindataviewer.client.service;

import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.client.manager.WeaponManager;
import space.xiami.project.genshindataviewer.domain.ResultDO;
import space.xiami.project.genshindataviewer.domain.model.Weapon;

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

    public ResultDO<Long> getWeaponId(Byte lang, String name){
        Long ret = weaponManager.getWeaponId(lang, name);
        if(ret == null){
            return ResultDO.buildErrorResult("查询失败");
        }
        return ResultDO.buildSuccessResult(ret);
    }

    public ResultDO<Weapon> getWeapon(Byte lang, String name){
        Weapon ret = weaponManager.getWeapon(lang, name);
        if(ret == null){
            return ResultDO.buildErrorResult("查询失败");
        }
        return ResultDO.buildSuccessResult(ret);
    }

    public ResultDO<Weapon> getWeapon(Byte lang, Long id){
        Weapon ret = weaponManager.getWeapon(lang, id);
        if(ret == null){
            return ResultDO.buildErrorResult("查询失败");
        }
        return ResultDO.buildSuccessResult(ret);
    }
}
