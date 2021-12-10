package com.leverx.dealerstat.service.impl;

import com.leverx.dealerstat.dto.converter.GameObjectConverter;
import com.leverx.dealerstat.dto.GameObjectDTO;
import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.entity.enums.Permission;
import com.leverx.dealerstat.exception.AccessDeniedException;
import com.leverx.dealerstat.exception.AlreadyExistsException;
import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.entity.GameObject;
import com.leverx.dealerstat.repository.GameObjectRepository;
import com.leverx.dealerstat.security.AuthenticatedUserFactory;
import com.leverx.dealerstat.service.GameObjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GameObjectServiceImpl implements GameObjectService {

    private final GameObjectConverter gameObjectConverter;
    private final GameObjectRepository gameObjectRepository;
    private final AuthenticatedUserFactory userFactory;

    public GameObjectServiceImpl(GameObjectConverter gameObjectConverter,
                                 GameObjectRepository gameObjectRepository,
                                 AuthenticatedUserFactory userFactory) {
        this.gameObjectConverter = gameObjectConverter;
        this.gameObjectRepository = gameObjectRepository;
        this.userFactory = userFactory;
    }

    @Override
    @Transactional(readOnly = true)
    public GameObjectDTO getById(Long gameObjectId) {
        GameObject gameObject = gameObjectRepository.findById(gameObjectId).orElseThrow(() -> {
            throw new NotFoundException("Game object is not found");
        });
        return gameObjectConverter.convertToDTO(gameObject);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GameObjectDTO> getAll() {
        return gameObjectRepository.findAll().stream()
                .map(gameObjectConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GameObjectDTO save(GameObjectDTO gameObject) {
        if (gameObjectRepository.existsByTitle(gameObject.getTitle())) {
            throw new AlreadyExistsException("Game object is already exists");
        }
        return gameObjectConverter.convertToDTO(gameObjectRepository.save(gameObjectConverter.convertToModel(gameObject)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GameObjectDTO> getAllByCurrentUser() {
        UserDTO user = userFactory.currentUser();
        return gameObjectRepository.findAllByAuthorId(user.getId()).stream()
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
        if (!Objects.equals(gameObject.getAuthorId(), user.getId())
                && !user.getRole().getPermissions().contains(Permission.UPDATE)) {
            throw new AccessDeniedException("It is not your game object");
        }
        return gameObjectConverter.convertToDTO(gameObjectRepository.save(gameObjectConverter.convertToModel(gameObject)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        UserDTO user = userFactory.currentUser();
        if (!user.getId().equals(gameObjectRepository.getById(id).getId())
                && !user.getRole().getPermissions().contains(Permission.DELETE)) {
            throw new AccessDeniedException("It is not your comment");
        }
        gameObjectRepository.deleteById(id);
    }
}
