package space.xiami.project.genshindataviewer.client;

import space.xiami.project.genshindataviewer.domain.ResultDO;
import space.xiami.project.genshindataviewer.domain.model.Avatar;
import space.xiami.project.genshindataviewer.domain.model.TeamResonance;
import space.xiami.project.genshindataviewer.domain.model.TeamResonanceGroup;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Xiami
 */
public interface AvatarService {

    ResultDO<Map<String, Long>> getAvatarIds(Byte lang);

    ResultDO<Long> getAvatarId(Byte lang, String name);

    ResultDO<Avatar> getAvatar(Byte lang, String name, Byte elementalType);

    ResultDO<Avatar> getAvatar(Byte lang, Long id, Byte elementalType);

    ResultDO<TeamResonance> getTeamResonance(Byte lang, Long id);

    ResultDO<TeamResonanceGroup> getTeamResonanceGroup(Byte lang, List<Long> ids, List<Byte> appointElementalTypes);
}
