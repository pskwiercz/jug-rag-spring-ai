package com.pskwiercz.jugragspringai.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class InitializeVectorStore implements CommandLineRunner {

    @Autowired
    VectorStore vectorStore;

    @Value("${app.resource}")
    private Resource documentResource;

    @Override
    public void run(String... args) {

        try {
            log.info("Loading documents into vector store from resource: {}", documentResource.toString());
            TikaDocumentReader documentReader = new TikaDocumentReader(documentResource);
            TextSplitter textSplitter = new TokenTextSplitter();
            vectorStore.accept(
                    textSplitter.apply(documentReader.get())
            );

        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        log.info("Vector store loaded");
    }
}



















