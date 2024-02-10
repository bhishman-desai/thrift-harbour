package tech.group15.thriftharbour.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Data
@Entity
public class AuctionSaleListing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int auctionSaleListingID;

    private String productName;

    private String productDescription;

    private Float startingBid;

    private Float highestBid;

    private String currentHighestBidUserMail;

    private String category;

    private String SellerEmail;

    @Default
    private boolean active = true;

    @Default
    private boolean isApproved = false;

    private boolean isRejected;

    @Default
    private boolean isSold = false;

    private LocalDate dateOfApproval;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
}
