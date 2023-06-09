package capstonServer.capstonServer.service;



import capstonServer.capstonServer.dto.request.GPTCompletionChatRequest;
import capstonServer.capstonServer.dto.response.CompletionResponse.Message;
import capstonServer.capstonServer.dto.request.GPTCompletionRequest;
import capstonServer.capstonServer.dto.response.CompletionChatResponse;
import capstonServer.capstonServer.dto.response.CompletionResponse;
import capstonServer.capstonServer.entity.GPTAnswer;
import capstonServer.capstonServer.entity.GPTQuestion;
import capstonServer.capstonServer.repository.GPTAnswerRepository;
import capstonServer.capstonServer.repository.GPTQuestionRepository;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.service.OpenAiService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GPTChatRestService {
    private final OpenAiService openAiService;

    private final GPTQuestionRepository questionRepository;

    private final GPTAnswerRepository answerRepository;

    @Transactional
    public CompletionResponse completion(final GPTCompletionRequest restRequest) {
        CompletionResult result = openAiService.createCompletion(GPTCompletionRequest.of(restRequest));
        CompletionResponse response = CompletionResponse.of(result);

        List<String> messages = response.getMessages().stream()
                .map(Message::getText)
                .collect(Collectors.toList());

        GPTAnswer gptAnswer = saveAnswer(messages);
        saveQuestion(restRequest.getPrompt(), gptAnswer);

        return response;
    }

    @Transactional
    public CompletionChatResponse completionChat(GPTCompletionChatRequest gptCompletionChatRequest) {
        ChatCompletionResult chatCompletion = openAiService.createChatCompletion(
                GPTCompletionChatRequest.of(gptCompletionChatRequest));

        CompletionChatResponse response = CompletionChatResponse.of(chatCompletion);

        List<String> messages = response.getMessages().stream()
                .map(CompletionChatResponse.Message::getMessage)
                .collect(Collectors.toList());

        GPTAnswer gptAnswer = saveAnswer(messages);

        saveQuestion(gptCompletionChatRequest.getContent(), gptAnswer);

        return response;
    }

    private void saveQuestion(String question, GPTAnswer answer) {
        GPTQuestion questionEntity = new GPTQuestion(question, answer);
        questionRepository.save(questionEntity);
    }

    private GPTAnswer saveAnswer(List<String> response) {

        String answer = response.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.joining());

        return answerRepository.save(new GPTAnswer(answer));
    }

}