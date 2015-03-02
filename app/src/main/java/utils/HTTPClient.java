package utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by WZQ on 2015/2/28.
 */
public class HTTPClient {
    public static String AnalyzingOfJson(String url, Context context) throws Exception {

        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = new DefaultHttpClient().execute(httpGet);
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            String result = EntityUtils.toString(httpResponse.getEntity());
            System.out.println("result:" + result);
            JSONArray jsonArray = new JSONArray("[" + result + "]");
            String message = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject != null) {
                    String errorCode = jsonObject.getString("errorCode");
                    switch (errorCode) {
                        case "20":
                            Toast.makeText(context, "要翻译的文本过长", Toast.LENGTH_SHORT).show();
                            break;
                        case "30 ":
                            Toast.makeText(context, "无法进行有效的翻译", Toast.LENGTH_SHORT).show();
                            break;
                        case "40":
                            Toast.makeText(context, "不支持的语言类型", Toast.LENGTH_SHORT).show();
                            break;
                        case "50":
                            Toast.makeText(context, "无效的key", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            message = jsonObject.getString("query");
                            String translation = jsonObject.getString("translation");
                            message += "\t" + translation;
                            if (jsonObject.has("basic")) {
                                JSONObject basic = jsonObject.getJSONObject("basic");
                                if (basic.has("phonetic")) {
                                    String phonetic = basic.getString("phonetic");
                                    message += "\n\t" + phonetic;
                                }
                                if (basic.has("phonetic")) {
                                    String explains = basic.getString("explains");
                                    message += "\n\t" + explains;
                                }
                            }
                            if (jsonObject.has("web")) {
                                String web = jsonObject.getString("web");
                                JSONArray webString = new JSONArray("[" + web + "]");
                                message += "\n网络释义：";
                                JSONArray webArray = webString.getJSONArray(0);
                                int count = 0;
                                while (!webArray.isNull(count)) {

                                    if (webArray.getJSONObject(count).has("key")) {
                                        String key = webArray.getJSONObject(count).getString("key");
                                        message += "\n\t<" + (count + 1) + ">" + key;
                                    }
                                    if (webArray.getJSONObject(count).has("value")) {
                                        String value = webArray.getJSONObject(count).getString("value");
                                        message += "\n\t   " + value;
                                    }
                                    count++;
                                }
                            }
                            break;
                    }
                }
            }

            return message;
        } else {
            Toast.makeText(context, "提取异常", Toast.LENGTH_SHORT).show();
            return "No Data!";
        }
    }
}
