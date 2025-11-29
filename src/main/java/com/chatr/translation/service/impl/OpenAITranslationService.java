package com.chatr.translation.service.impl;

import com.chatr.shared.enums.PreferredLanguage;
import com.chatr.translation.service.TranslationService;
import com.openai.client.OpenAIClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.chat.completions.ChatCompletionSystemMessageParam;
import com.openai.models.chat.completions.ChatCompletionUserMessageParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenAITranslationService implements TranslationService {
    private final OpenAIClient openAIClient;

    @Override
    public String translate(String text, PreferredLanguage preferredLanguage) {
        String systemInstruction = String.format(
                "You are a top-notch translator with a deep understanding of cultural context, slang, and idioms. " +
                "Your task is to identify the language of the user's text and translate it into %s. " +
                "Maintain the original tone and nuance. Output ONLY the translated text without any conversational filler.",
                preferredLanguage.name()
        );

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model(ChatModel.GPT_4O)
                .addMessage(ChatCompletionSystemMessageParam.builder()
                        .content(systemInstruction)
                        .build())
                .addMessage(ChatCompletionUserMessageParam.builder()
                        .content(text)
                        .build())
                .build();

        ChatCompletion chatCompletion = openAIClient.chat()
                .completions()
                .create(params);

        if (chatCompletion.choices().isEmpty()) {
            return text;
        }

        return chatCompletion.choices().get(0).message().content().orElse(text);
    }
}
