package de.unibayreuth.se.campuscoffee.api.dtos;

import de.unibayreuth.se.campuscoffee.domain.model.CampusType;
import de.unibayreuth.se.campuscoffee.domain.model.PosType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
public class ReviewDto {
    @Null
    private Long id;

    @Null
    private LocalDateTime createdAt;

    @Nullable
    private Long posId;

    @Nullable
    private Long authorId;

    @NotEmpty
    private String review;

    private boolean approved;
}