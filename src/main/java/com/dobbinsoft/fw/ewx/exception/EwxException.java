package com.dobbinsoft.fw.ewx.exception;

import com.dobbinsoft.fw.core.exception.CoreExceptionDefinition;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class EwxException extends RuntimeException {

    @JsonProperty("errcode")
    private Integer errcode;
    @JsonProperty("errmsg")
    private String errmsg;

    public EwxException(Integer errcode, String errmsg) {
        super(errmsg);
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public ServiceException toServiceException() {
        return new ServiceException(String.format("[%d]%s", this.errcode, this.errmsg.split(", hint")[0]), CoreExceptionDefinition.THIRD_PART_SERVICE_EXCEPTION.getCode());
    }

}
