package org.fullstack.libapp.requestmodels;

import java.util.Optional;
import lombok.Data;

@Data
public class AddReviewRequest {

  private Double rating;

  private Long bookId;

  private Optional<String> reviewDescription;
}
