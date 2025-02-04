package ynov.architecture.ds.borrowingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ynov.architecture.ds.borrowingservice.dto.enums.EMembershipType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private EMembershipType membershipType; // Regular, Premium
    private boolean isLocked = false;
    private Integer nombreMaxEmprunt;
}
