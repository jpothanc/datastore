package com.ibit.datastore.controllers;

import com.ibit.datastore.factory.CommandFactory;
import com.ibit.datastore.models.CommandRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/api/v1/command")
@CrossOrigin
public class CommandController {

    private CommandFactory commandFactory;
    @Autowired
    public CommandController(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @CrossOrigin
    @GetMapping("/webCounter")
    public ResponseEntity<Integer> getWebCounter(@RequestParam String siteName) {
        int counter = 0;
        try {
            counter = commandFactory.getWebCounterCommand().getCounter(siteName);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(counter);

    }

    @GetMapping("/incrementCounter")
    public Mono<ResponseEntity<Integer>> create(@RequestParam String siteName) {
        ResponseEntity<Integer> response = null;
        try {
            commandFactory.getWebCounterCommand().incrementCounter(siteName);
            response = getWebCounter(siteName);
        } catch (Exception e) {
            return Mono.just(ResponseEntity.notFound().build());
        }

        return Mono.just(response);
    }

}
