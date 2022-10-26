package space.xiami.project.genshindataviewer.web.controler;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import space.xiami.project.genshindataviewer.client.AvatarService;
import space.xiami.project.genshindataviewer.domain.ResultDO;
import space.xiami.project.genshindataviewer.domain.ResultVO;
import space.xiami.project.genshindataviewer.domain.model.Avatar;
import space.xiami.project.genshindataviewer.domain.model.TeamResonance;
import space.xiami.project.genshindataviewer.domain.model.TeamResonanceGroup;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Xiami
 */
@RestController
@RequestMapping("/avatar")
public class AvatarController {

    @Resource
    private AvatarService avatarService;

    @RequestMapping("/list")
    @ResponseBody
    public ResultVO list(@RequestParam(name = "lang", defaultValue = "0") Byte lang){
        ResultDO<Map<String, Long>> ret = avatarService.getAvatarIds(lang);
        if(ret.isSuccess()){
            return ResultVO.buildSuccessResult(ret.getResult());
        }
        return ResultVO.buildErrorResult(ret.getMsg());
    }

    @RequestMapping("/get_id")
    @ResponseBody
    public ResultVO getId(@RequestParam(name = "lang", defaultValue = "0") Byte lang, String name){
        ResultDO<Long> ret = avatarService.getAvatarId(lang, name);
        if(ret.isSuccess()){
            return ResultVO.buildSuccessResult(ret.getResult());
        }
        return ResultVO.buildErrorResult(ret.getMsg());
    }

    @RequestMapping("/get_by_name")
    @ResponseBody
    public ResultVO getByName(@RequestParam(name = "lang", defaultValue = "0") Byte lang, String name, @RequestParam(name = "elementalType", defaultValue = "")Byte elementalType){
        ResultDO<Avatar> ret = avatarService.getAvatar(lang, name, elementalType);
        if(ret.isSuccess()){
            return ResultVO.buildSuccessResult(ret.getResult());
        }
        return ResultVO.buildErrorResult(ret.getMsg());
    }

    @RequestMapping("/get_by_id")
    @ResponseBody
    public ResultVO getById(@RequestParam(name = "lang", defaultValue = "0") Byte lang, Long id, @RequestParam(name = "elementalType", defaultValue = "")Byte elementalType){
        ResultDO<Avatar> ret = avatarService.getAvatar(lang, id, elementalType);
        if(ret.isSuccess()){
            return ResultVO.buildSuccessResult(ret.getResult());
        }
        return ResultVO.buildErrorResult(ret.getMsg());
    }

    @RequestMapping("/get_team_resonance")
    @ResponseBody
    public ResultVO getTeamResonance(@RequestParam(name = "lang", defaultValue = "0") Byte lang, Long id){
        ResultDO<TeamResonance> ret = avatarService.getTeamResonance(lang, id);
        if(ret.isSuccess()){
            return ResultVO.buildSuccessResult(ret.getResult());
        }
        return ResultVO.buildErrorResult(ret.getMsg());
    }

    @RequestMapping("/match_team_resonance")
    @ResponseBody
    public ResultVO matchTeamResonance(@RequestParam(name = "lang", defaultValue = "0") Byte lang, String idAppoint){
        List<Long> ids = new ArrayList<>();
        List<Byte> appoints = new ArrayList<>();
        String[] idAppoints = idAppoint.split("~");
        Arrays.stream(idAppoints).forEach(idAppointStr -> {
            String[] strArr = idAppointStr.split("@");
            ids.add(Long.parseLong(strArr[0]));
            if(strArr.length > 1){
                appoints.add(Byte.parseByte(strArr[1]));
            }else {
                appoints.add(null);
            }
        });
        ResultDO<TeamResonanceGroup> ret = avatarService.getTeamResonanceGroup(lang, ids, appoints);
        if(ret.isSuccess()){
            return ResultVO.buildSuccessResult(ret.getResult());
        }
        return ResultVO.buildErrorResult(ret.getMsg());
    }
}
