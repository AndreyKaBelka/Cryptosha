package com.messenger.cryptosha.resource;

import com.andreyka.crypto.ECPointParseException;
import com.messenger.cryptosha.dto.KeyDTO;
import com.messenger.cryptosha.service.KeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(keyService.getUserKeyDTO(id));
    }

    @PostMapping("/")
    public ResponseEntity<String> setKeyById(@RequestBody KeyDTO body) throws ECPointParseException {
        keyService.setUserPublicKey(body);
        return ResponseEntity.ok("Success!");
    }
}
