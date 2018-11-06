package kr.hellodev.support.commons.binding;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 06/11/2018 3:21 PM
 */
@NoArgsConstructor
@AllArgsConstructor
class BindingError {

    @Expose
    @Getter
    @Setter
    private String code;
    @Expose
    @Getter
    @Setter
    private String message;
}
