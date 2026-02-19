package com.jumbotail.shippingchargeestimator.payloads.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerRequest {

    @NotNull(message = "Seller Name is Required")
    @Size(max = 100 , message = "Seller Name cannot exceed 100 characters")
    private String sellerName;

    @NotBlank(message = "Phone Number is Required")
    @Pattern(
            regexp = "^\\+?[0-9]{10,15}$",
            message = "Invalid phone number format"
    )
    private String phoneNumber;

    @NotNull(message = "Latitude is Required")
    @DecimalMin(value = "-90.0" , message = "Latitude must be >= -90")
    @DecimalMax(value = "90.0" , message = "Latitude must be <= 90")
    private double latitude;

    @NotNull(message = "Longitude is Required")
    @DecimalMin(value = "-180.0" , message = "Longitude must be >= -180")
    @DecimalMax(value = "90.0" , message = "Longitude must be <= 180")
    private double longitude;

}
