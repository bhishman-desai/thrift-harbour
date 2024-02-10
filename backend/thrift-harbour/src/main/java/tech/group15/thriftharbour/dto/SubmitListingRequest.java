package tech.group15.thriftharbour.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import tech.group15.thriftharbour.enums.SellCategoryEnum;

import java.util.Date;
import java.util.List;

@Data
public class SubmitListingRequest {

    private String productName;

    private String productDescription;

    private List<MultipartFile> productImages;

    private double productPrice;

    private String productCategory;

    private SellCategoryEnum sellCategory;

    private Date auctionSlot;
}
