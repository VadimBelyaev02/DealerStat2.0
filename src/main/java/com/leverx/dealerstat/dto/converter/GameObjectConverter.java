package com.leverx.dealerstat.dto.converter;

import com.leverx.dealerstat.dto.GameObjectDTO;
import com.leverx.dealerstat.entity.GameObject;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.repository.GameObjectRepository;
import com.leverx.dealerstat.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class GameObjectConverter {

    private final GameObjectRepository gameObjectRepository;
    private final UserRepository userRepository;

    public GameObjectConverter(GameObjectRepository gameObjectRepository, UserRepository userRepository) {
        this.gameObjectRepository = gameObjectRepository;
        this.userRepository = userRepository;
    }

    public GameObjectDTO convertToDTO(GameObject gameObject) {
        final Long id = gameObject.getId();
        final LocalDate createdAt = gameObject.getDateOfCreating();
        final LocalDate updatedAt = gameObject.getDateOfUpdating();
        final String title = gameObject.getTitle();
        final String description = gameObject.getDescription();
        final Long authorId = gameObject.getAuthor().getId();
        final BigDecimal price = gameObject.getPrice();
        return GameObjectDTO.builder()
      //          .id(id)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .title(title)
                .description(description)
                .authorId(authorId)
                .price(price)
                .build();
    }

    public GameObject convertToModel(final GameObjectDTO gameObjectDTO) {
    //    final Long id = gameObjectDTO.getId();
        final String title = gameObjectDTO.getTitle();
        final String description = gameObjectDTO.getDescription();
     //   final User author = gameObjectRepository.getById(id).getAuthor();
        final User author = userRepository.getById(gameObjectDTO.getAuthorId());
        final BigDecimal price = gameObjectDTO.getPrice();
        final LocalDate createdAt = gameObjectDTO.getCreatedAt();
        final LocalDate updatedAt = gameObjectDTO.getUpdatedAt();
        return GameObject.builder()
                .title(title)
                .description(description)
                .author(author)
                .price(price)
                .dateOfCreating(createdAt)
                .dateOfUpdating(updatedAt)
                .build();
    }
}
