package sonnh.dev.assetsmanagement.deepseek;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class DeepSeekChatRequest {

    private String model;
    private List<Message> messages;
    private boolean stream = false;

    @Data
    @AllArgsConstructor
    public static class Message {
        private String role;    // system | user
        private String content;
    }
}
