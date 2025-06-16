package de.unibayreuth.se.campuscoffee.api.controller;

import de.unibayreuth.se.campuscoffee.api.dtos.PosDto;
import de.unibayreuth.se.campuscoffee.api.dtos.ReviewDto;
import de.unibayreuth.se.campuscoffee.api.dtos.UserDto;
import de.unibayreuth.se.campuscoffee.api.mapper.PosDtoMapper;
import de.unibayreuth.se.campuscoffee.api.mapper.ReviewDtoMapper;
import de.unibayreuth.se.campuscoffee.domain.exceptions.PosNotFoundException;
import de.unibayreuth.se.campuscoffee.domain.exceptions.ReviewNotFoundException;
import de.unibayreuth.se.campuscoffee.domain.exceptions.UserNotFoundException;
import de.unibayreuth.se.campuscoffee.domain.model.Review;
import de.unibayreuth.se.campuscoffee.domain.ports.PosService;
import de.unibayreuth.se.campuscoffee.domain.ports.ReviewService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.sound.sampled.ReverbType;
import java.util.List;

@OpenAPIDefinition(
        info = @Info(
                title = "CampusCoffee",
                version = "0.0.1"
        )
)

@Tag(name = "Reviews")
@Controller
@RequestMapping("/api/reviews")
@RequiredArgsConstructor

public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewDtoMapper reviewDtoMapper;

    @Operation(
            summary = "Get all reviews.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "array", implementation = ReviewDto.class)
                            ),
                            description = "All POS as a JSON array."
                    )
            }
    )

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getAll() {
        return ResponseEntity.ok(
                reviewService.getAll().stream()
                        .map(reviewDtoMapper::fromDomain)
                        .toList()
        );
    }


    @Operation(
            summary = "Get reviews by ID.",
            responses = {
        @ApiResponse(
                responseCode = "200",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ReviewDto.class)
                ),
                description = "The Review with the provided ID as a JSON object."
        ),
        @ApiResponse(
                responseCode = "400",
                description = "No Review with the provided ID could be found."
        )
    }
    )
    @GetMapping ("/{id}")
    public ResponseEntity<ReviewDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                reviewDtoMapper.fromDomain(reviewService.getById(id))

        );
    }



    @GetMapping("/filter")
    public ResponseEntity<List<ReviewDto>> getApprovedByPosId(@RequestParam("pos_id") Long posId) {
        return ResponseEntity.ok(
                reviewService.getApprovedByPos(posId).stream()
                        .map(reviewDtoMapper::fromDomain)
                        .toList()
        );
    }

    @Operation(
            summary = "Creates a new Review.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ReviewDto.class)
                            ),
                            description = "The new Review as a JSON object."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID is not empty."
                    )
            }
    )
    @PostMapping("")
    public ResponseEntity<ReviewDto> create(@RequestBody @Valid ReviewDto reviewDto) {
        return upsert(reviewDto);
    }

    private ResponseEntity<ReviewDto> upsert(ReviewDto reviewDto) throws ReviewNotFoundException {
        return ResponseEntity.ok(
                reviewDtoMapper.fromDomain(
                        reviewService.upsert(
                                reviewDtoMapper.toDomain(reviewDto)
                        )
                )
        );
    }
}
