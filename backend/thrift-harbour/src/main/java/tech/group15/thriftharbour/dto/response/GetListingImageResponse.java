package tech.group15.thriftharbour.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetListingImageResponse {

    String listingId;

    List<String> imageURLs;
}
