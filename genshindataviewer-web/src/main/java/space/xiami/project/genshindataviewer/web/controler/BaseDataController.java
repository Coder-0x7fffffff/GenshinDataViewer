package space.xiami.project.genshindataviewer.web.controler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import space.xiami.project.genshindataviewer.client.BaseDataService;
import space.xiami.project.genshindataviewer.domain.ResultDO;
import space.xiami.project.genshindataviewer.domain.ResultVO;
import space.xiami.project.genshindataviewer.web.scheduled.ResourceScheduled;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/data")
public class BaseDataController {

    public static final Logger log = LoggerFactory.getLogger(BaseDataController.class);

    @Resource
    private BaseDataService baseDataService;

    @Resource
    private ResourceScheduled resourceScheduled;

    @RequestMapping("/text")
    @ResponseBody
    public ResultVO getText(Byte lang, Long id){
        ResultDO<String> result = baseDataService.getTextByLangId(lang, id);
        if(result.isSuccess()){
            return ResultVO.buildSuccessResult(result.getResult());
        }
        return ResultVO.buildErrorResult(result.getMsg());
    }

    @RequestMapping("/allEnum")
    @ResponseBody
    public ResultVO allEnum(){
        ResultDO<Map<String, Map<Byte, String>>> result = baseDataService.getValueDescOfAllEnums();
        if(result.isSuccess()){
            return ResultVO.buildSuccessResult(result.getResult());
        }
        return ResultVO.buildErrorResult(result.getMsg());
    }

    @RequestMapping("refresh")
    @ResponseBody
    public ResultVO refresh(){
        String error = null;
        try{
            resourceScheduled.refreshResource();
        }catch (Exception e){
            error = e.getMessage();
        }
        if(error == null){
            return ResultVO.buildSuccessResult("强制刷新数据成功");
        }
        return ResultVO.buildErrorResult(error);
    }
}
