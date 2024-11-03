# jug-rag-spring-ai

## Code for JUG presentation - "RAG SpringAI"

## Guide

Set the OpenAI API key as an environment variable named OPENAI_API_KEY
```
export OPENAI_API_KEY=your_api_key
```
Run application
```
mvn spring-boot:run
```

The first time you run it, it will take a some time to initialize vector store. Wait for following info
`Vector store loaded` in the logs.

Now you can use application and ask questions.
```
http :8080/ask question="your_question"
```
 


