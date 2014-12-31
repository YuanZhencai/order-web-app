package common.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import common.MsgCode;
import common.Severity;
import common.vo.Message;
import common.vo.MessageVo;
import play.libs.Json;

/**
 * Created by yuan on 9/22/14.
 */
public class MessageUtil {

  private MessageVo mesageVo;

  private MessageUtil() {
    super();
  }

  private static ThreadLocal<MessageUtil> instance = new ThreadLocal<MessageUtil>() {
    protected MessageUtil initialValue() {
      return (new MessageUtil());
    }
  };

  public static MessageUtil getInstance() {
    return instance.get();
  }

  public void setMessage(Message message) {
    mesageVo = new MessageVo(message);
  }

  public void setMessage(Message message, Object value) {
    setMessage(message);
    mesageVo.setValue(value);
  }

    public String setMessageHeader(Object value){
        Message message =  mesageVo == null ? new Message(Severity.ERROR, MsgCode.OPERATE_FAILURE) : mesageVo.getMessage();
        ObjectNode json = Json.newObject();
        json.put("message", Json.toJson(message));
        json.put("headerValue", Json.toJson(value));
        return json.toString();
    }


    public JsonNode toJson() {
    return Json.toJson(mesageVo);
  }

  public JsonNode msgToJson(Message message, Object value) {
    setMessage(message, value);
    return toJson();
  }

  public JsonNode msgToJson(Message message) {
    setMessage(message);
    return toJson();
  }

}
