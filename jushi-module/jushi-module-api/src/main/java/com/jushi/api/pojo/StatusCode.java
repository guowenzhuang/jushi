package com.jushi.api.pojo;

public enum  StatusCode {
     OK("20000","成功"),
     ERROR("20001","失败"),
     LOGINERROR("20002","用户名或密码错误"),
     ACCESSERROR("20003","权限不足"),
     REMOTEERROR("20004","远程调用失败"),
     REPERROR("20005","重复操作"),
     PARAMETERILLEGAL("20006","参数不合法");
     private String value;
     private String label;
      StatusCode(String value,String label){
          this.value=value;
          this.label=label;
     }

     public String value() {
          return value;
     }

     public StatusCode setValue(String value) {
          this.value = value;
          return this;
     }

     public String label() {
          return label;
     }

     public StatusCode setLabel(String label) {
          this.label = label;
          return this;
     }
}
