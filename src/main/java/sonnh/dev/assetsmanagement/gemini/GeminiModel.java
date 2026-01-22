package sonnh.dev.assetsmanagement.gemini;
public enum GeminiModel {

    GEMINI_3_FLASH("gemini-3-flash"),
    GEMINI_2_5_FLASH("gemini-2.5-flash"),
    GEMINI_2_5_FLASH_LITE("gemini-2.5-flash-lite"),

    GEMMA_3_27B("gemma-3-27b"),
    GEMMA_3_12B("gemma-3-12b"),
    GEMMA_3_4B("gemma-3-4b");

    private final String modelName;

    GeminiModel(String modelName) {
        this.modelName = modelName;
    }

    public String getModelName() {
        return modelName;
    }
}

