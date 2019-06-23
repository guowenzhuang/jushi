package com.jushi.api.pojo;

/**
 * @author 80795
 */
public enum  StatusCode {
     /**
      * 返回成功时
      */
     OK("20000","成功"),
     /**
      * 返回失败时
      */
     ERROR("20001","失败"),
     /**
      * 用户名或密码错误
      */
     LOGINERROR("20002","用户名或密码错误"),
     /**
      * 权限不足
      */
     ACCESSERROR("20003","权限不足"),
     /**
      * 远程调用失败
      */
     REMOTEERROR("20004","远程调用失败"),
     /**
      * 重复操作
      */
     REPERROR("20005","重复操作"),
     /**
      * 参数不合法
      */
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
