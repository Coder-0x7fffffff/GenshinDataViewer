package space.xiami.project.genshindataviewer.web.controler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import space.xiami.project.genshindataviewer.client.WeaponService;
import space.xiami.project.genshindataviewer.domain.ResultDO;
import space.xiami.project.genshindataviewer.domain.ResultVO;
import space.xiami.project.genshindataviewer.domain.model.Weapon;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/weapon")
public class WeaponController {

    @Resource
    private WeaponService weaponService;

    @RequestMapping("/list")
    @ResponseBody
    public ResultVO list(@RequestParam(name = "lang", defaultValue = "0") Byte lang){
        ResultDO<Map<String, Long>> ret = weaponService.getWeaponIds(lang);
        if(ret.isSuccess()){
            return ResultVO.buildSuccessResult(ret);
        }
        return ResultVO.buildErrorResult(ret.getMsg());
    }

    @RequestMapping("/get_id")
    @ResponseBody
    public ResultVO getId(@RequestParam(name = "lang", defaultValue = "0") Byte lang, String name){
        ResultDO<Long> ret = weaponService.getWeaponId(lang, name);
        if(ret.isSuccess()){
            return ResultVO.buildSuccessResult(ret);
        }
        return ResultVO.buildErrorResult(ret.getMsg());
    }

    @RequestMapping("/get_by_name")
    @ResponseBody
    public ResultVO getByName(@RequestParam(name = "lang", defaultValue = "0") Byte lang, String name){
        ResultDO<Weapon> ret = weaponService.getWeapon(lang, name);
        if(ret.isSuccess()){
            return ResultVO.buildSuccessResult(ret);
        }
        return ResultVO.buildErrorResult(ret.getMsg());
    }

    @RequestMapping("/get_by_id")
    @ResponseBody
    public ResultVO getById(@RequestParam(name = "lang", defaultValue = "0") Byte lang, Long id){
        ResultDO<Weapon> ret = weaponService.getWeapon(lang, id);
        if(ret.isSuccess()){
            return ResultVO.buildSuccessResult(ret);
        }
        return ResultVO.buildErrorResult(ret.getMsg());
    }
}
