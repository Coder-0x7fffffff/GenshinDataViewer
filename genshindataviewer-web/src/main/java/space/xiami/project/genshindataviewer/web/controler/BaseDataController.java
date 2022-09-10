package space.xiami.project.genshindataviewer.web.controler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import space.xiami.project.genshindataviewer.domain.ResultDO;
import space.xiami.project.genshindataviewer.web.domain.ResultVO;
import space.xiami.project.genshindataviewer.client.service.BaseDataService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/base")
public class BaseDataController {

    public static final Logger log = LoggerFactory.getLogger(BaseDataController.class);

    @Resource
    private BaseDataService baseDataService;

    @RequestMapping("/text")
    @ResponseBody
    public ResultVO<String> text(Byte lang, Long id){
        ResultDO<String> result = baseDataService.getTextByLangId(lang, id);
        log.info("request with lang={}, id={}", lang, id);
        if(result.isSuccess()){
            return ResultVO.buildSuccessResult(result.getResult());
        }
        return ResultVO.buildErrorResult(result.getMsg());
    }
}
