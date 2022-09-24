package space.xiami.project.genshindataviewer.service.factory;

import java.util.Set;

/**
 * @author Xiami
 */
public interface FileBaseFactory {

    /**
     * 获取相关文件set
     * @return 相关文件set
     */
    Set<String> relatedFilePathSet();

    /**
     * 加载数据
     * @param path 路径
     */
    void reload(String path);
}
