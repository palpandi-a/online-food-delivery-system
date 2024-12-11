package com.agent.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agent.service.model.Agent;
import com.agent.service.service.AgentService;

@RestController
@RequestMapping("/agents")
public class AgentController {

    private final AgentService service;

    public AgentController(AgentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createAgent(@RequestBody Agent agent) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createAgent(agent));
    }

    @GetMapping("/{agentId}")
    public ResponseEntity<?> getAgentById(@PathVariable int agentId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAgent(agentId));
    }

    @GetMapping
    public ResponseEntity<?> getAllAgents(@RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllAgents(from, limit));
    }

    @DeleteMapping("/{agentId}")
    public ResponseEntity<?> deleteAnAgent(@PathVariable int agentId) {
        service.deleteAnAgent(agentId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{agentId}")
    public ResponseEntity<?> updateAgent(@PathVariable int agentId, @RequestBody Agent agent) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateAgent(agentId, agent));
    }

    @GetMapping("/available")
    public ResponseEntity<?> getAvailableAgents() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAvailableAgents());
    }

}
