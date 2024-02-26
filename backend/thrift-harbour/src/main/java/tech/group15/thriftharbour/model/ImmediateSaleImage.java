package tech.group15.thriftharbour.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ImmediateSaleImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageID;

    private String immediateSaleListingID;

    private String imageURL;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

}
