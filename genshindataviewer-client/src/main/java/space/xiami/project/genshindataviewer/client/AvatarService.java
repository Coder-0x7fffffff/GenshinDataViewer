package space.xiami.project.genshindataviewer.client;

import space.xiami.project.genshindataviewer.domain.ResultDO;
import space.xiami.project.genshindataviewer.domain.model.Avatar;

import java.util.Map;

/**
 * @author Xiami
 */
public interface AvatarService {

    ResultDO<Map<String, Long>> getAvatarIds(Byte lang);

    ResultDO<Long> getAvatarId(Byte lang, String name);

    ResultDO<Avatar> getAvatar(Byte lang, String name);

    ResultDO<Avatar> getAvatar(Byte lang, Long id);
}
