package com.agent.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agent.service.model.Agent;
import com.agent.service.model.Status;

import java.util.List;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Integer> {

    Agent findByPhoneNo(String phoneNo);

    List<Agent> findByStatus(Status status);

}
