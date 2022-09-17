package space.xiami.project.genshindataviewer.client;

import space.xiami.project.genshindataviewer.domain.ResultDO;
import space.xiami.project.genshindataviewer.domain.model.Weapon;

import java.util.Map;

public interface WeaponService {

    ResultDO<Map<String, Long>> getWeaponIds(Byte lang);

    ResultDO<Long> getWeaponId(Byte lang, String name);

    ResultDO<Weapon> getWeapon(Byte lang, String name);

    ResultDO<Weapon> getWeapon(Byte lang, Long id);
}
