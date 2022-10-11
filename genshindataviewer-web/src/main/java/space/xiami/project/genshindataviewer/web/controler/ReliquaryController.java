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
import java.util.Map;

/**
 * @author Xiami
 */
@RestController
@RequestMapping("/reliquary")
public class ReliquaryController {

    @Resource
    private ReliquaryService reliquaryService;

    @RequestMapping("/list_set")
    @ResponseBody
    public ResultVO<Map<String, Long>> listSet(@RequestParam(name = "lang", defaultValue = "0") Byte lang){
        ResultDO<Map<String, Long>> ret = reliquaryService.getReliquarySetId(lang);
        if(ret.isSuccess()){
            return ResultVO.buildSuccessResult(ret.getResult());
        }
        return ResultVO.buildErrorResult(ret.getMsg());
    }

    @RequestMapping("/list")
    @ResponseBody
    public ResultVO<Map<String, Long>> list(@RequestParam(name = "lang", defaultValue = "0") Byte lang){
        ResultDO<Map<String, Long>> ret = reliquaryService.getReliquaryId(lang);
        if(ret.isSuccess()){
            return ResultVO.buildSuccessResult(ret.getResult());
        }
        return ResultVO.buildErrorResult(ret.getMsg());
    }

    @RequestMapping("/get_set_id")
    @ResponseBody
    public ResultVO<Long> getSetId(@RequestParam(name = "lang", defaultValue = "0") Byte lang, String name){
        ResultDO<Long> ret = reliquaryService.getReliquarySetId(lang, name);
        if(ret.isSuccess()){
            return ResultVO.buildSuccessResult(ret.getResult());
        }
        return ResultVO.buildErrorResult(ret.getMsg());
    }

    @RequestMapping("/get_id")
    @ResponseBody
    public ResultVO<Long> getId(@RequestParam(name = "lang", defaultValue = "0") Byte lang, String name){
        ResultDO<Long> ret = reliquaryService.getReliquaryId(lang, name);
        if(ret.isSuccess()){
            return ResultVO.buildSuccessResult(ret.getResult());
        }
        return ResultVO.buildErrorResult(ret.getMsg());
    }

    @RequestMapping("/get_by_name")
    @ResponseBody
    public ResultVO<Reliquary> getByName(@RequestParam(name = "lang", defaultValue = "0") Byte lang, String name){
        ResultDO<Reliquary> ret = reliquaryService.getReliquary(lang, name);
        if(ret.isSuccess()){
            return ResultVO.buildSuccessResult(ret.getResult());
        }
        return ResultVO.buildErrorResult(ret.getMsg());
    }

    @RequestMapping("/get_by_id")
    @ResponseBody
    public ResultVO<Reliquary> getById(@RequestParam(name = "lang", defaultValue = "0") Byte lang, Long id){
        ResultDO<Reliquary> ret = reliquaryService.getReliquary(lang, id);
        if(ret.isSuccess()){
            return ResultVO.buildSuccessResult(ret.getResult());
        }
        return ResultVO.buildErrorResult(ret.getMsg());
    }

    @RequestMapping("/get_set_by_name")
    @ResponseBody
    public ResultVO<ReliquarySet> getSetByName(@RequestParam(name = "lang", defaultValue = "0") Byte lang, String name){
        ResultDO<ReliquarySet> ret = reliquaryService.getReliquarySet(lang, name);
        if(ret.isSuccess()){
            return ResultVO.buildSuccessResult(ret.getResult());
        }
        return ResultVO.buildErrorResult(ret.getMsg());
    }

    @RequestMapping("/get_set_by_id")
    @ResponseBody
    public ResultVO<ReliquarySet> getSetById(@RequestParam(name = "lang", defaultValue = "0") Byte lang, Long id){
        ResultDO<ReliquarySet> ret = reliquaryService.getReliquarySet(lang, id);
        if(ret.isSuccess()){
            return ResultVO.buildSuccessResult(ret.getResult());
        }
        return ResultVO.buildErrorResult(ret.getMsg());
    }
}
