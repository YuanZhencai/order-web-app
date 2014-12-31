package common.vo;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

/**
 * Created by yuan on 9/22/14.
 */
public class MessageVo<V> {

  private Message message;
  private V value;

  public MessageVo() {
  }

  public MessageVo(Message message) {
    this.message = message;
  }

  public Message getMessage() {
    return message;
  }

  public void setMessage(Message message) {
    this.message = message;
  }

  public void setValue(V value) {
    this.value = value;
  }

  public V getValue() {
    return this.value;
  }

  public JsonNode toJson() {
    return Json.toJson(this);
  }

}
