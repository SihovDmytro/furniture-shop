package com.springtraining.furnitureshop.service;

import com.springtraining.furnitureshop.domain.Producer;
import com.springtraining.furnitureshop.repository.ProducerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProducerServiceTest {

    @Mock
    private ProducerRepository producerRepository;

    @InjectMocks
    private ProducerService producerService;

    @Test
    void findAll_shouldReturnAllProducers() {
        List<Producer> producers = List.of(new Producer("ACME"), new Producer("BestFurniture"));
        when(producerRepository.findAll()).thenReturn(producers);

        List<Producer> result = producerService.findAll();

        assertEquals(2, result.size());
        verify(producerRepository, times(1)).findAll();
    }
}

