package sonnh.dev.assetsmanagement.client;

public interface AiClient {

    /**
     * Gửi prompt và trả về kết quả dạng text thuần
     */
    String generate(String prompt);
}