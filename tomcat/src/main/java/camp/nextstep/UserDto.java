package camp.nextstep;

import java.util.Map;

public record UserDto(String account, String password, String email) {

    public static UserDto map(Map<String, Object> messageBody) {
        String account = (String) messageBody.get("account");
        String password = (String) messageBody.get("password");
        String email = (String) messageBody.get("email");
        return new UserDto(account, password, email);
    }
}
