package site.easy.to.build.crm.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import site.easy.to.build.crm.util.POV;

@Getter
@Setter
@AllArgsConstructor
public class ResponseJSON<T> {
    @JsonView(POV.Public.class)
    private int code;
    @JsonView(POV.Public.class)
    private String message;
    @JsonView(POV.Public.class)
    private T data;

    public ResponseJSON(int code,String message){
        this.code=code;
        this.message=message;
    }
}
