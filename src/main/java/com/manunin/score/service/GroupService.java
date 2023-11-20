package com.manunin.score.service;

import com.manunin.score.model.Group;
import com.manunin.score.repository.GroupRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(final GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public void createGroup(final Group group) {
        group.setUrl(generateUrl(group.getName() + group.getDateTime()));
        groupRepository.save(group);
    }

    public void updateGroup(final Group group) {
        groupRepository.save(group);
    }

    public void deleteGroup(final long id) {
        groupRepository.deleteById(id);
    }

    public List<Group> getGroups() {
        return groupRepository.findAll();
    }

    public boolean existsByName(final String name) {
        return groupRepository.existsByName(name);
    }

    @Transactional
    public List<Group> findByStringWithPagination(final String inputString, final Pageable pageable) {
        return groupRepository.findAllByInputStringWithPagination(inputString, pageable).get().collect(Collectors.toList());
    }

    @Transactional
    public List<Group> findByString(final String inputString) {
        return groupRepository.findAllByInputString(inputString);
    }

    public Group findById(final Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    public boolean findStudentInGroup(final Long groupId, final Long studentId) {
        Optional<Group> byId = groupRepository.findById(groupId);
        byId.orElseThrow(() -> new RuntimeException("Group not found"));
        return byId.get().getStudents().stream().anyMatch(student -> student.getStudent().getId() == studentId);
    }

    public Optional<Group> getGroupNameByUrl(String url) {
        return groupRepository.findByUrl(url);
    }

    public Optional<String> getGroupIdByUrl(String url) {
        return groupRepository.findByUrl(url).map(Group::getId).map(String::valueOf);
    }

    public Optional<String> getUrlByGroupId(String groupId) {
        return groupRepository.findById(Long.valueOf(groupId)).map(Group::getUrl);
    }

    private String generateUrl(String groupId) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(groupId.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().encodeToString(hash).substring(0, 8); // Можете изменить длину короткой ссылки здесь
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
