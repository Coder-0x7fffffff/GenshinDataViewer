package space.xiami.project.genshindataviewer.client;

import space.xiami.project.genshindataviewer.domain.ResultDO;
import space.xiami.project.genshindataviewer.domain.model.Reliquary;
import space.xiami.project.genshindataviewer.domain.model.ReliquarySet;

import java.util.List;
import java.util.Map;

/**
 * @author Xiami
 */
public interface ReliquaryService {

    ResultDO<Map<String, Long>> getReliquarySetId(Byte lang);

    ResultDO<Long> getReliquarySetId(Byte lang, String name);

    ResultDO<Map<String, Long>> getReliquaryId(Byte lang);

    ResultDO<Long> getReliquaryId(Byte lang, String name);

    ResultDO<Reliquary> getReliquary(Byte lang, String name);

    ResultDO<Reliquary> getReliquary(Byte lang, Long id);

    ResultDO<ReliquarySet> getReliquarySet(Byte lang, String name);

    ResultDO<ReliquarySet> getReliquarySet(Byte lang, Long id);
}
