package com.agent.service.validator;

import org.springframework.stereotype.Component;

import com.agent.service.constants.Constants;
import com.agent.service.exception.ValidationException;
import com.agent.service.model.Agent;
import com.agent.service.repository.AgentRepository;

@Component
public class ValidatorAPIImpl implements ValidatorAPI {

    private final AgentRepository repository;

    public ValidatorAPIImpl(AgentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validateAgentData(Agent agent) {
        throwExceptionIfTrue(agent == null, Constants.INVALID_PAYLOAD);
        validateName(agent.getName());
        validatePhoneNo(agent.getPhoneNo());
    }

    private void throwExceptionIfTrue(boolean value, String message) {
        if (value) {
            throw new ValidationException(message);
        }
    }

    private void validateName(String name) {
        throwExceptionIfTrue(name == null, "name is a mandatory field");
        throwExceptionIfTrue(name.isEmpty() || name.isBlank(), Constants.INVALID_NAME);
    }

    private void validatePhoneNo(String phoneNo) {
        throwExceptionIfTrue(phoneNo == null, "phoneNo is a mandatory field");
        validatePhoneData(phoneNo);
    }

    public void validatePhoneData(String phoneNo) {
        throwExceptionIfTrue(!phoneNo.matches(Constants.PHONE_NO_REGEX), Constants.INVALID_PHONE_NO);
        throwExceptionIfTrue(repository.findByPhoneNo(phoneNo) != null, "Phone no is already taken: " + phoneNo);
    }

}
