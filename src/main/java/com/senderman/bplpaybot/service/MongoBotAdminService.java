package com.senderman.bplpaybot.service;

import com.senderman.bplpaybot.model.BotAdmin;
import com.senderman.bplpaybot.repository.BotAdminRepository;
import org.springframework.stereotype.Service;

@Service
public class MongoBotAdminService implements BotAdminService {

    private final BotAdminRepository repo;

    public MongoBotAdminService(BotAdminRepository repo) {
        this.repo = repo;
    }

    @Override
    public BotAdmin save(BotAdmin botAdmin) {
        return repo.save(botAdmin);
    }

    @Override
    public void delete(BotAdmin botAdmin) {
        repo.delete(botAdmin);
    }

    @Override
    public void deleteById(long id) {
        repo.deleteById(id);
    }

    @Override
    public boolean existsById(long id) {
        return repo.existsById(id);
    }
}
