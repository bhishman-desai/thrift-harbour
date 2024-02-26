package tech.group15.thriftharbour.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AuctionSaleListing {
    @Id
    private String auctionSaleListingID;

    private String productName;

    private String productDescription;

    private double startingBid;

    private double highestBid;

    private String currentHighestBidUserMail;

    private String category;

    private String sellerEmail;

    private Date auctionSlot;

    @Default
    private boolean active = true;

    @Default
    private boolean isApproved = false;

    private boolean isRejected;

    private String approverEmail;

    private String messageFromApprover;

    @Default
    private boolean isSold = false;

    private Date dateOfApproval;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
}
