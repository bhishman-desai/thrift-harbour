package tech.group15.thriftharbour.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ImmediateSaleListing {
    @Id
    private String immediateSaleListingID;

    private String productName;

    private String productDescription;

    private double price;

    private String category;

    private String SellerEmail;

    @Builder.Default
    private boolean active = true;

    @Builder.Default
    private boolean isApproved = false;

    private boolean isRejected;

    @Builder.Default
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
