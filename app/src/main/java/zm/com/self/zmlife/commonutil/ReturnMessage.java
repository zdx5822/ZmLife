package zm.com.self.zmlife.commonutil;

import java.util.List;
import java.util.Map;

/**
 * 返回消息定义
 * @author 连波
 *
 */

//"resultcode":"112",
//        "reason":"超过每日可允许请求次数!",
//        "result":null,
//        "error_code":10012

public class ReturnMessage {
    private String reason;
    private List<Map<String, String>> result;
    private String error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<Map<String, String>> getResult() {
        return result;
    }

    public void setResult(List<Map<String, String>> result) {
        this.result = result;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }
}
