package com.springtraining.furnitureshop.service;

import com.springtraining.furnitureshop.domain.Producer;
import com.springtraining.furnitureshop.repository.ProducerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProducerService {
    private final ProducerRepository producerRepository;

    public ProducerService(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    /**
     * Returns all producers.
     *
     * @return a {@link List} of all {@link Producer} entities
     */
    public List<Producer> findAll() {
        return producerRepository.findAll();
    }
}
