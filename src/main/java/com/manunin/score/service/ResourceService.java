package com.manunin.score.service;

import com.manunin.score.model.Resource;
import com.manunin.score.repository.ResourceRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceService {
    private final ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public void deleteById(final Long id) {
        resourceRepository.deleteById(id);
    }

    public boolean existsByName(final String name) {
        return resourceRepository.existsByName(name);
    }

    public void updateResource(final Resource resource) {
        resourceRepository.save(resource);
    }

    @Transactional
    public List<Resource> findByStringWithPagination(final String inputString, final Pageable pageable) {
        return resourceRepository.findAllByInputStringWithPagination(inputString, pageable).get().collect(Collectors.toList());
    }

    @Transactional
    public List<Resource> findByString(final String inputString) {
        return resourceRepository.findAllByInputString(inputString);
    }
}
