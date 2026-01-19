package sonnh.dev.assetsmanagement.gemini;

import java.util.List;

public class GeminiRequestBuilder {

    public static GeminiRequest fromPrompt(String prompt) {
        GeminiRequest.Part part = new GeminiRequest.Part();
        part.setText(prompt);

        GeminiRequest.Content content = new GeminiRequest.Content();
        content.setRole("user");
        content.setParts(List.of(part));

        GeminiRequest request = new GeminiRequest();
        request.setContents(List.of(content));

        return request;
    }
}
