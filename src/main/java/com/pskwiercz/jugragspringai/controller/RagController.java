package com.pskwiercz.jugragspringai.controller;

import com.pskwiercz.jugragspringai.model.Answer;
import com.pskwiercz.jugragspringai.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class RagController {

    final ChatModel chatModel;
    final VectorStore vectorStore;

    @Value("classpath:/templates/prompt-template.st")
    private Resource promptTemplate;

    @PostMapping("/ask")
    public Answer ask(@RequestBody Question question) {

        List<Document> documents = vectorStore.similaritySearch(SearchRequest
                .query(question.question()).withTopK(5));

        List<String> contentList = documents.stream().map(Document::getContent).toList();

        PromptTemplate promptTemplate = new PromptTemplate(this.promptTemplate);

        Prompt prompt = promptTemplate.create(Map.of(
                "input", question.question(),
                "documents", String.join("\n", contentList)));

        // contentList.forEach(System.out::println);

        ChatResponse response = chatModel.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
    }

}











