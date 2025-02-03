package group.controller;

import group.service.EmbeddingService;
import group.dto.event.EventDto;
import group.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatClient chatClient;
    private final EventService eventService;
    private final EmbeddingService embeddingService;

    @GetMapping("/chat")
    public String chat(@RequestParam String message) {
        List<Double> queryEmbedding = embeddingService.generateEmbedding(message);
        List<EventDto> similarEvents = eventService.findSimilarEvents(queryEmbedding, 5);

        if (similarEvents.isEmpty()) {
            return "Sorry, I couldn't find any events matching your request. But don't worry! Let's try something else.";
        } else {
            StringBuilder eventsMessage = new StringBuilder("Here are some events I found:\n");
            for (EventDto event : similarEvents) {
                eventsMessage.append("- [")
                        .append(event.getTitle())
                        .append("](")
                        .append(event.getEventLink())
                        .append("): ")
                        .append(event.getDescription())
                        .append("\n");
            }

            String systemPrompt = "You are a personal chat assistant of a dream realiser. Respond in an encouraging, motivational, and knowledgeable manner. " +
                    "When the user says 'Hi' or 'Hello', respond with a friendly greeting and ask how they'd like to help. Do not show and search events on this stage " +
                    "Always format links in Markdown so they are clickable: [message](link). " +
                    "If the user is looking for events, always format event titles in bold using Markdown: **Title**. " +
                    "If the user is looking for events related to animals, only include events that clearly mention animals, pets, shelters, or wildlife. " +
                    "If the user is looking for events related to children, only include events that clearly mention children, education, or mentorship. " +
                    "Exclude any events that are not directly related to the user's request.";

            return chatClient
                    .prompt()
                    .system(systemPrompt)
                    .user("The user said: \"" + message + "\". Here are the events I found:\n" + eventsMessage + "\nPlease provide a motivational response and filter out irrelevant events.")
                    .call()
                    .content();
        }
    }
}

