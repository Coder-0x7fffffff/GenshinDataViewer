package space.xiami.project.genshindataviewer.client.manager;

import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.client.factory.WeaponFactory;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class WeaponManager {
    @Resource
    private WeaponFactory weaponFactory;

    public Map<String, Long> getWeaponIds(Byte language){
        return weaponFactory.getName2Ids(language);
    }
}
