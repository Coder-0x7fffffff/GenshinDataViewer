package space.xiami.project.genshindataviewer.service.factory;

import java.util.Set;

public interface FileBaseFactory {

    Set<String> relatedFilePathSet();
    void reload(String path);
}
