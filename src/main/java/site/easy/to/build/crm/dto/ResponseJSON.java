package site.easy.to.build.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseJSON<T> {
    private int code;
    private String message;
    private T data;

    public ResponseJSON(int code,String message){
        this.code=code;
        this.message=message;
    }
}
