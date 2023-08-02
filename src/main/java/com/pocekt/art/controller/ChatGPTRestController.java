package com.pocekt.art.controller;

import capstonServer.capstonServer.dto.request.GPTCompletionChatRequest;
import capstonServer.capstonServer.dto.request.GPTCompletionRequest;
import capstonServer.capstonServer.dto.response.CompletionChatResponse;
import capstonServer.capstonServer.dto.response.CompletionResponse;
import capstonServer.capstonServer.service.GPTChatRestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chatgpt/rest")
@RequiredArgsConstructor
public class ChatGPTRestController {

    private final GPTChatRestService gptChatRestService;

    @PostMapping("/completion")
    public CompletionResponse completion(final @RequestBody GPTCompletionRequest gptCompletionRequest) {

        return gptChatRestService.completion(gptCompletionRequest);
    }

    @PostMapping("/completion/chat")
    public CompletionChatResponse completionChat(final @RequestBody GPTCompletionChatRequest gptCompletionChatRequest) {
        return gptChatRestService.completionChat(gptCompletionChatRequest);
    }

}