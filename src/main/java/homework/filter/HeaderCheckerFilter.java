package homework.filter;

import homework.service.ChatRoomService;
import homework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class HeaderCheckerFilter extends OncePerRequestFilter {
    private static final String X_ROOM_ID_NAME = "X-ROOM-ID";
    private static final String X_USER_ID_NAME = "X-USER-ID";

    private final ChatRoomService chatRoomService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long chatRoomId = getValueFromHeader(X_ROOM_ID_NAME, request);
        if (!chatRoomService.isExistRoom(chatRoomId)) {
            throw new SecurityException("Room Id is not valid!");
        }

        long userId = getValueFromHeader(X_USER_ID_NAME, request);
        if (!userService.isExistUser(userId)) {
            throw new SecurityException("User Id is not valid!");
        }

        filterChain.doFilter(request, response);
    }

    private long getValueFromHeader(String key, HttpServletRequest request) {
        String value = request.getHeader(key);
        checkHeaderIsNull(value);

        return Long.parseLong(value);
    }

    private void checkHeaderIsNull(String value) {
        if (Objects.isNull(value)) {
            throw new SecurityException("Empty Header is not allowed!");
        }
    }
}
