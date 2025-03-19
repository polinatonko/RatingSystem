package com.example.ratingsystem.service.request;

import com.example.ratingsystem.domain.entities.SubmitRequest;
import com.example.ratingsystem.domain.enums.RequestStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service that provides CRUD-operations for managing {@link SubmitRequest} objects.
 */
public interface SubmitRequestService {
    /**
     * Create request to post the comment to the seller.
     *
     * @param request {@link SubmitRequest} with comment details
     * @return created {@link SubmitRequest}
     */
    SubmitRequest createCommentRequest(SubmitRequest request);

    /**
     * Create request to post the comment and register new seller.
     *
     * @param request {@link SubmitRequest} with comment and seller details
     * @return created {@link SubmitRequest}
     */
    SubmitRequest createRegistrationRequest(SubmitRequest request);

    /**
     * Get request via provided id.
     *
     * @param id {@link UUID} - id of the request
     * @return {@link Optional}
     */
    Optional<SubmitRequest> get(UUID id);

    /**
     * Get all requests with specific status, null value of parameter is ignored.
     *
     * @param status {@link RequestStatus} - status of the request
     * @return {@link List} of the requests
     */
    List<SubmitRequest> getByStatus(RequestStatus status);

    /**
     * Approve waiting request via id.
     *
     * @param id {@link UUID} - id of the request
     * @return updated {@link SubmitRequest}
     */
    SubmitRequest approve(UUID id);

    /**
     * Reject waiting request via id.
     *
     * @param id {@link UUID} - id of the request
     * @return updated {@link SubmitRequest}
     */
    SubmitRequest reject(UUID id);
}