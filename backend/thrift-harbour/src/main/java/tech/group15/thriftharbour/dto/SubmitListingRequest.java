package tech.group15.thriftharbour.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;
import tech.group15.thriftharbour.enums.SellCategoryEnum;

import java.util.Date;
import java.util.List;

@Data
public class SubmitListingRequest {

    @NotBlank
    private String productName;

    @NotBlank
    private String productDescription;

    @NotBlank
    private List<MultipartFile> productImages;

    @NotBlank
    private double productPrice;

    @NotBlank
    private String productCategory;

    @NotBlank
    private SellCategoryEnum sellCategory;

    @Nullable
    private String auctionSlot;
}
