package com.springtraining.furnitureshop.service;

import com.springtraining.furnitureshop.domain.Producer;
import com.springtraining.furnitureshop.repository.ProducerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProducerService {
    private final ProducerRepository producerRepository;

    public ProducerService(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    public List<Producer> findAll() {
        return producerRepository.findAll();
    }
}
