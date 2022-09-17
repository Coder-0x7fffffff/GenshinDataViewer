package space.xiami.project.genshindataviewer.client;

import space.xiami.project.genshindataviewer.domain.ResultDO;

import java.util.Map;

public interface BaseDataService {

    ResultDO<String> getTextByLangId(Byte lang, Long id);

    ResultDO<Map<Byte, String>> getValueDescOfEnum(String name);

    ResultDO<Map<String, Map<Byte, String>>> getValueDescOfAllEnums();
}
