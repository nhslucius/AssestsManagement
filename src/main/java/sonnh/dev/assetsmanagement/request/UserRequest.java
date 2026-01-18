package sonnh.dev.assetsmanagement.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import sonnh.dev.assetsmanagement.exeption.ValidationMessage;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request tạo user")
public class UserRequest {

    @NotBlank(message = "Name must not be blank")
    @Schema(example = "Lucius")
    @NotBlank(message = ValidationMessage.REQUIRED)
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Invalid email format")
    @Schema(example = "lucius@gmail.com")
    private String email;

    @NotNull(message = "Date of birth is required")
    @Schema(example = "1995-01-01")
    private LocalDate dob;
}