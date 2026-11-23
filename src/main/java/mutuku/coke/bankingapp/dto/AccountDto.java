package mutuku.coke.bankingapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long id;
    @NotBlank(message = "Account Name Must A value")
    @Size(min = 3, max = 50, message = "Name must be 3 to 50 characters")
    private String accountHolderName;
    @PositiveOrZero(message = "Balance cannot be negative")
    private double balance;

}
