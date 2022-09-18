package space.xiami.project.genshindataviewer.service.manager;

import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.domain.json.AvatarExcelConfigData;
import space.xiami.project.genshindataviewer.domain.model.Avatar;
import space.xiami.project.genshindataviewer.service.factory.AvatarFactory;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class AvatarManager {

    @Resource
    private AvatarFactory avatarFactory;

    public Map<String, Long> getAvatarIds(Byte language){
        return avatarFactory.getName2Ids(language);
    }

    public Long getAvatarId(Byte lang, String name){
        return avatarFactory.getIdByName(lang, name);
    }

    public Avatar getAvatar(Byte lang, String name){
        return getAvatar(lang, getAvatarId(lang, name));
    }

    public Avatar getAvatar(Byte lang, Long id){
        if(lang == null || id == null){
            return null;
        }
        AvatarExcelConfigData excelConfigData = avatarFactory.getAvatar(id);
        if(excelConfigData == null){
            return null;
        }
        return convert(lang, excelConfigData);
    }
    
    public Avatar convert(Byte lang, AvatarExcelConfigData excelConfigData){
        //TODO convert
        return new Avatar();
    }
}
