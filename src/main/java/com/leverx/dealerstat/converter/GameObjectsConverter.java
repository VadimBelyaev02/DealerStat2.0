package com.leverx.dealerstat.converter;

import com.leverx.dealerstat.dto.GameObjectDTO;
import com.leverx.dealerstat.model.GameObject;
import com.leverx.dealerstat.model.User;
import com.leverx.dealerstat.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class GameObjectsConverter {

    private final UsersService usersService;

    @Autowired
    public GameObjectsConverter(UsersService usersService) {
        this.usersService = usersService;
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
        final User author = usersService.findById(gameObjectDTO.getAuthorId());
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
