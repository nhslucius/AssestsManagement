package sonnh.dev.assetsmanagement.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class LmStudioChatRequest {

    private String model;
    private List<Message> messages;
    private Double temperature = 0.2;

    @Data
    @AllArgsConstructor
    public static class Message {
        private String role;   // system | user
        private String content;
    }
}