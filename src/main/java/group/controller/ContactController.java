package group.controller;

import group.dto.contact.ContactDto;
import group.dto.contact.ContactRequestDto;
import group.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactDto createContact(@RequestBody ContactRequestDto contactRequestDto) {
        return contactService.createContact(contactRequestDto);
    }
}
