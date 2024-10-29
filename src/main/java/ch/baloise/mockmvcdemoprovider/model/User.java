package ch.baloise.mockmvcdemoprovider.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class User {
    private final String id;
    private final String name;
}
