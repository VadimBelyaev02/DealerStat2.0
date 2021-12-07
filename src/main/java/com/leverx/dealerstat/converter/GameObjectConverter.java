package com.leverx.dealerstat.converter;

import com.leverx.dealerstat.dto.GameObjectDTO;
import com.leverx.dealerstat.entity.GameObject;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class GameObjectConverter {

    private final UserService userService;

    @Autowired
    public GameObjectConverter(UserService userService) {
        this.userService = userService;
    }

    public GameObjectDTO convertToDTO(GameObject gameObject) {
        final String title = gameObject.getTitle();
        final String description = gameObject.getDescription();
        final Long authorId = gameObject.getAuthor().getId();
        final BigDecimal price = gameObject.getPrice();
        return GameObjectDTO.builder()
                .title(title)
                .description(description)
                .authorId(authorId)
                .price(price)
                .build();
    }

    public GameObject convertToModel(final GameObjectDTO gameObjectDTO) {
        final String title = gameObjectDTO.getTitle();
        final String description = gameObjectDTO.getDescription();
        final User author = userService.findById(gameObjectDTO.getAuthorId());
        final BigDecimal price = gameObjectDTO.getPrice();
        final Date createdAt = new Date();
        final Date updatedAt = new Date();
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
