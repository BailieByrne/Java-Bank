package Security;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;


@Getter
@Builder
public class LoginResponse {

    private final String accessToken;

   
}