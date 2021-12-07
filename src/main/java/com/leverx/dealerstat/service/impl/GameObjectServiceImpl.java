package com.leverx.dealerstat.service.impl;

import com.leverx.dealerstat.converter.GameObjectConverter;
import com.leverx.dealerstat.dto.GameObjectDTO;
import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.entity.GameObject;
import com.leverx.dealerstat.repository.GameObjectRepository;
import com.leverx.dealerstat.security.AuthenticatedUserFactory;
import com.leverx.dealerstat.service.GameObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameObjectServiceImpl implements GameObjectService {

    private final GameObjectConverter gameObjectConverter;
    private final GameObjectRepository gameObjectRepository;
    private final AuthenticatedUserFactory userFactory;

    @Override
    @Transactional(readOnly = true)
    public GameObjectDTO findById(Long gameObjectId) {
        GameObject gameObject = gameObjectRepository.findById(gameObjectId).orElseThrow(() -> {
            throw new NotFoundException("Game object is not found");
        });
        return gameObjectConverter.convertToDTO(gameObject);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GameObjectDTO> findAll() {
        return gameObjectRepository.findAll().stream()
                .map(gameObjectConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GameObjectDTO save(GameObjectDTO gameObject) {
        return gameObjectConverter.convertToDTO(gameObjectRepository.save(gameObjectConverter.convertToModel(gameObject));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GameObjectDTO> findAllByAuthorId(Long id) {
        return gameObjectRepository.findAllByAuthorId(id).stream()
                .map(gameObjectConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GameObjectDTO update(GameObjectDTO gameObject) {
        UserDTO user = userFactory.currentUser();
        if (!gameObjectRepository.existsById(gameObject.getId())) {
            throw new NotFoundException("Game object is not found");
        }
        if (!gameObjectRepository.findById(gameObject.getId()).getAuthor().getId().equals(user.getId())) {
            return ResponseEntity.notFound().build();
        }
      //  repository.save(gameObject);
        // save?
    }


}
