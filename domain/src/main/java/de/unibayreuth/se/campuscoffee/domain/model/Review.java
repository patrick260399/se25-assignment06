package de.unibayreuth.se.campuscoffee.domain.model;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Domain class that stores the review metadata.
 */
@Data
@Builder(toBuilder = true)
//@RequiredArgsConstructor
//@AllArgsConstructor
public class Review implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L; // required to clone objects (see TestFixtures class).

    private Long id;
    private LocalDateTime createdAt;
    private Long posId;
    private Long authorId;
    private String review;
    private int approvalCount;

    // TODO: define Review domain class (uncomment Lombok annotations after defining the class)
}
