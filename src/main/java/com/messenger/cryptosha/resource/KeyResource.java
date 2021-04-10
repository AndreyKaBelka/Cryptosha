package com.messenger.cryptosha.resource;

import com.andreyka.crypto.ECPointParseException;
import com.messenger.cryptosha.dto.KeyDTO;
import com.messenger.cryptosha.service.KeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;

@RestController
@RequestMapping("/key")
public class KeyResource {
    private final KeyService keyService;

    @Autowired
    public KeyResource(KeyService keyService) {
        this.keyService = keyService;
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<KeyDTO> getKeyById(@PathVariable Integer id) {
        try {
            KeyDTO keyDTO = keyService.getUserKeyDTO(id);
            return ResponseEntity.ok(keyDTO);
        } catch (InvalidParameterException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> setKeyById(@RequestBody KeyDTO body) throws ECPointParseException {
        keyService.setUserPublicKey(body);
        return ResponseEntity.ok().build();
    }
}
