package space.xiami.project.genshindataviewer.domain.json;

/**
 * @author Xiami
 */
public class DocumentExcelConfigData {
    private Long id;
    private String titleTextMapHash;
    private Long contentLocalizedId;
    private String previewPath;
    private String documentType;
    private String videoPath;
    private Long subtitleID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitleTextMapHash() {
        return titleTextMapHash;
    }

    public void setTitleTextMapHash(String titleTextMapHash) {
        this.titleTextMapHash = titleTextMapHash;
    }

    public Long getContentLocalizedId() {
        return contentLocalizedId;
    }

    public void setContentLocalizedId(Long contentLocalizedId) {
        this.contentLocalizedId = contentLocalizedId;
    }

    public String getPreviewPath() {
        return previewPath;
    }

    public void setPreviewPath(String previewPath) {
        this.previewPath = previewPath;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public Long getSubtitleID() {
        return subtitleID;
    }

    public void setSubtitleID(Long subtitleID) {
        this.subtitleID = subtitleID;
    }
}
