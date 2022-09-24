package space.xiami.project.genshindataviewer.web.controler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import space.xiami.project.genshindataviewer.client.ReliquaryService;
import space.xiami.project.genshindataviewer.domain.ResultDO;
import space.xiami.project.genshindataviewer.domain.ResultVO;
import space.xiami.project.genshindataviewer.domain.model.Reliquary;
import space.xiami.project.genshindataviewer.domain.model.ReliquarySet;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Xiami
 */
@RestController
@RequestMapping("/reliquary")
public class ReliquaryController {

    @Resource
    private ReliquaryService reliquaryService;

    @RequestMapping("/list")
    @ResponseBody
    public ResultVO list(@RequestParam(name = "lang", defaultValue = "0") Byte lang){
        ResultDO<Map<String, List<Long>>> ret = reliquaryService.getReliquaryIds(lang);
        if(ret.isSuccess()){
            return ResultVO.buildSuccessResult(ret);
        }
        return ResultVO.buildErrorResult(ret.getMsg());
    }

    @RequestMapping("/get_id")
    @ResponseBody
    public ResultVO getId(@RequestParam(name = "lang", defaultValue = "0") Byte lang, String name){
        ResultDO<List<Long>> ret = reliquaryService.getReliquaryId(lang, name);
        if(ret.isSuccess()){
            return ResultVO.buildSuccessResult(ret);
        }
        return ResultVO.buildErrorResult(ret.getMsg());
    }

    @RequestMapping("/get_by_name")
    @ResponseBody
    public ResultVO getByName(@RequestParam(name = "lang", defaultValue = "0") Byte lang, String name){
        ResultDO<List<Reliquary>> ret = reliquaryService.getReliquaries(lang, name);
        if(ret.isSuccess()){
            return ResultVO.buildSuccessResult(ret);
        }
        return ResultVO.buildErrorResult(ret.getMsg());
    }

    @RequestMapping("/get_by_id")
    @ResponseBody
    public ResultVO getById(@RequestParam(name = "lang", defaultValue = "0") Byte lang, Long id){
        ResultDO<Reliquary> ret = reliquaryService.getReliquary(lang, id);
        if(ret.isSuccess()){
            return ResultVO.buildSuccessResult(ret);
        }
        return ResultVO.buildErrorResult(ret.getMsg());
    }

    @RequestMapping("/get_set_by_id")
    @ResponseBody
    public ResultVO getSetById(@RequestParam(name = "lang", defaultValue = "0") Byte lang, Long id){
        ResultDO<ReliquarySet> ret = reliquaryService.getReliquarySet(lang, id);
        if(ret.isSuccess()){
            return ResultVO.buildSuccessResult(ret);
        }
        return ResultVO.buildErrorResult(ret.getMsg());
    }
}
