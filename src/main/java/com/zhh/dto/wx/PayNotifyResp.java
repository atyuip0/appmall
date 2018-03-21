package com.zhh.dto.wx;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "xml")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayNotifyResp {

    public PayNotifyResp(String return_code, String return_msg) {
        this.return_code = return_code;
        this.return_msg = return_msg;
    }

    @JacksonXmlCData(value = true)
    private String return_code;
    @JacksonXmlCData(value = true)
    private String return_msg;

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }
}
