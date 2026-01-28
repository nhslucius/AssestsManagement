package sonnh.dev.assetsmanagement.deepseek;

import lombok.Data;

import java.util.List;

@Data
public class DeepSeekChatResponse {

    private List<Choice> choices;

    @Data
    public static class Choice {
        private Message message;
    }

    @Data
    public static class Message {
        private String role;
        private String content;
    }
}

