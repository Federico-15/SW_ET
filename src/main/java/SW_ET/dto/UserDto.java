package SW_ET.dto;

import SW_ET.entity.User;
import SW_ET.entity.types.UserRole;
import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class UserDto {
    @NotBlank(message = "사용자 ID는 필수입니다.")
    @Size(min = 4, message = "사용자 ID는 최소 4자 이상이어야 합니다.")
    private String userId;

    @NotBlank(message = "닉네임은 필수입니다.")
    private String userNickName;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String userPassword;

    @NotBlank(message = "비밀번호 확인은 필수입니다.")
    private String confirmPassword;

    public User toUser() {
        User user = new User();
        user.setUserId(this.userId);
        user.setUserNickName(this.userNickName);
        user.setUserPassword(this.userPassword);
        user.setUserRole(UserRole.USER);
        return user;
    }
}