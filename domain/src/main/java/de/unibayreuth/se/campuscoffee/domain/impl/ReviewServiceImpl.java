package de.unibayreuth.se.campuscoffee.domain.impl;

import de.unibayreuth.se.campuscoffee.domain.exceptions.ReviewNotFoundException;
import de.unibayreuth.se.campuscoffee.domain.exceptions.UserNotFoundException;
import de.unibayreuth.se.campuscoffee.domain.model.Review;
import de.unibayreuth.se.campuscoffee.domain.model.User;
import de.unibayreuth.se.campuscoffee.domain.ports.ReviewDataService;
import de.unibayreuth.se.campuscoffee.domain.ports.ReviewService;
import de.unibayreuth.se.campuscoffee.domain.ports.UserDataService;
import de.unibayreuth.se.campuscoffee.domain.ports.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewDataService reviewDataService;

    @Override
    public void clear() {
        reviewDataService.clear();
    }

    @Override
    @NonNull
    public List<Review> getAll() {
        return reviewDataService.getAll();
    }

    @Override
    @NonNull
    public Review getById(@NonNull Long id) throws ReviewNotFoundException {
        return reviewDataService.getById(id);
    }

    @Override
    @NonNull
    public List<Review> getApprovedByPos(@NonNull Long posId) throws ReviewNotFoundException {
        return reviewDataService.getByPos(posId);
    }

    @Override
    public Review approve(@NonNull Review review, @NonNull User user) {
        if (review.getAuthor().getId().equals(user.getId())) {
            throw new IllegalArgumentException("User cannot approve their own review.");
        }

        review.setApprovalCount(review.getApprovalCount() + 1);
        return reviewDataService.save(review);
    }

}
