package space.xiami.project.genshindataviewer.service.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import space.xiami.project.genshindataviewer.domain.json.TeamResonanceExcelConfigData;
import space.xiami.project.genshindataviewer.service.util.PathUtil;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Xiami
 */
@Component
public class TeamResonanceFactory extends AbstractFileBaseFactory {

    public static final Logger log = LoggerFactory.getLogger(TeamResonanceFactory.class);

    public static final String teamResonanceExcelConfigDataFile = "TeamResonanceExcelConfigData.json";

    private static final Set<String> relatedFilePath = new HashSet<>();

    /**
     * teamResonanceGroupId -> teamResonanceId -> DO
     */
    private final Map<Long, Map<Long ,TeamResonanceExcelConfigData>> teamResonanceExcelConfigDataMap = new HashMap<>();

    private final Map<Long, TeamResonanceExcelConfigData> teamResonanceExcelConfigDataMapById = new HashMap<>();

    static {
        relatedFilePath.add(PathUtil.getExcelBinOutputDirectory() + teamResonanceExcelConfigDataFile);
    }

    @Override
    public Set<String> relatedFilePathSet() {
        return relatedFilePath;
    }

    @Override
    public void load(String path) {
        try{
            if(path.endsWith(SPLASH + teamResonanceExcelConfigDataFile)) {
                List<TeamResonanceExcelConfigData> array = readJsonArray(path, TeamResonanceExcelConfigData.class);
                for (TeamResonanceExcelConfigData data : array) {
                    Map<Long, TeamResonanceExcelConfigData> innerMap = teamResonanceExcelConfigDataMap.computeIfAbsent(data.getTeamResonanceGroupId(), v -> new HashMap<>());
                    if(innerMap.containsKey(data.getTeamResonanceId())){
                        log.warn("Ignore same teamResonanceGroupId={}", data.getTeamResonanceId());
                        continue;
                    }
                    innerMap.put(data.getTeamResonanceId(), data);
                    teamResonanceExcelConfigDataMapById.put(data.getTeamResonanceId(), data);
                }
            }
        } catch (IOException e) {
            log.error("load error", e);
        }
    }

    @Override
    protected void clear(String path) {
        if(path.endsWith(SPLASH + teamResonanceExcelConfigDataFile)) {
            teamResonanceExcelConfigDataMap.clear();
            teamResonanceExcelConfigDataMapById.clear();
        }
    }

    public List<TeamResonanceExcelConfigData> getTeamResonanceList(){
        return teamResonanceExcelConfigDataMap.values().stream()
                .map(Map::values)
                .flatMap(Collection::stream).collect(Collectors.toList());
    }

    public Map<Long ,TeamResonanceExcelConfigData> getByTeamResonanceGroupId(Long teamResonanceGroupId){
        readLock();
        Map<Long, TeamResonanceExcelConfigData> result = teamResonanceExcelConfigDataMap.get(teamResonanceGroupId);
        readUnlock();
        return result;
    }

    public TeamResonanceExcelConfigData getByTeamResonanceId( Long teamResonanceId){
        readLock();
        TeamResonanceExcelConfigData result = teamResonanceExcelConfigDataMapById.get(teamResonanceId);
        readUnlock();
        return result;
    }
}
