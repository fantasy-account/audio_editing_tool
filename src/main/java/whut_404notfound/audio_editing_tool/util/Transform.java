package whut_404notfound.audio_editing_tool.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import whut_404notfound.audio_editing_tool.exception.DemoException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
/**
 * @author whutrubbish
 * @create 2021-04-07-18:56
 */
@Component
public class Transform {

    private String APP_KEY="KsqtwrierczzL78KNQE3VUOk";
    private String SECRET_KEY= "5ya8owRGFfVmMKGynZhIMHljF7KABp4x";
    private String SCOPE="audio_voice_assistant_get";

    // 发音人选择, 基础音库：0为度小美，1为度小宇，3为度逍遥，4为度丫丫，
    // 精品音库：5为度小娇，103为度米朵，106为度博文，110为度小童，111为度小萌，默认为度小美
    private int per = 0;
    // 语速，取值0-15，默认为5中语速
    private int spd = 5;
    // 音调，取值0-15，默认为5中语调
    private int pit = 5;
    // 音量，取值0-9，默认为5中音量
    private  int vol = 5;
    // 下载的文件格式, 3：mp3(default) 4： pcm-16k 5： pcm-8k 6. wav
    private int aue = 6;

    String CUID = "1234567JAVA";

    public void setPer(int per) {// 精品音库：5为度小娇，103为度米朵，106为度博文，110为度小童，111为度小萌，默认为度小美
        this.per = per;
    }

    public void setSpd(int spd) {// 语速，取值0-15，默认为5中语速
        this.spd = spd;
    }

    public void setPit(int pit) { // 音调，取值0-15，默认为5中语调
        this.pit = pit;
    }

    public void setVol(int vol) {// 音量，取值0-9，默认为5中音量
        this.vol = vol;
    }

    public void setAue(int aue) {// 下载的文件格式, 3：mp3(default) 4： pcm-16k 5： pcm-8k 6. wav
        this.aue = aue;
    }

    public static void main(String[] args) {
        Transform transform=new Transform();
        try {
            transform.Character2Audio("操你妈了个逼","result2.wav");
            System.out.println(transform.Audio2Character("result.wav"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DemoException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String Audio2Character(String sourceFile) {

        TokenHolder holder = new TokenHolder(APP_KEY, SECRET_KEY, SCOPE);
        try {
            holder.resfresh();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DemoException e) {
            e.printStackTrace();
        }
        String token = holder.getToken();
        String result = null;
        /*if (METHOD_RAW) {
            result = runRawPostMethod(token);
        } else {
            result = runJsonPostMethod(token);
        }*/
        try {
            result = runJsonPostMethod(token,sourceFile);
        } catch (DemoException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        File file = new File(destinationFile);
//        FileWriter fo = new FileWriter(file);
//        fo.write(result);
//        fo.close();
//        System.out.println("Result also wrote into " + file.getAbsolutePath());
        return result;
    }
    public String runJsonPostMethod(String token,String FILENAME) throws DemoException, IOException, JSONException {

        int DEV_PID = 1537;
        String FORMAT = FILENAME.substring(FILENAME.length() - 3);
        byte[] content = getFileContent(FILENAME);
        String speech = base64Encode(content);
        int RATE = 16000;
        String URL = "http://vop.baidu.com/server_api";

        JSONObject params = new JSONObject();
        params.put("dev_pid", DEV_PID);
        //params.put("lm_id",LM_ID);//测试自训练平台需要打开注释
        params.put("format", FORMAT);
        params.put("rate", RATE);
        params.put("token", token);
        params.put("cuid", CUID);
        params.put("channel", "1");
        params.put("len", content.length);
        params.put("speech", speech);

        // System.out.println(params.toString());
        HttpURLConnection conn = (HttpURLConnection) new URL(URL).openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setDoOutput(true);
        conn.getOutputStream().write(params.toString().getBytes());
        conn.getOutputStream().close();
        String result = ConnUtil.getResponseString(conn);


        params.put("speech", "base64Encode(getFileContent(FILENAME))");
        //System.out.println("url is : " + URL);
        //System.out.println("params is :" + params.toString());


        return result;
    }
    private byte[] getFileContent(String filename) throws DemoException, IOException {
        File file = new File(filename);
        if (!file.canRead()) {
            System.err.println("文件不存在或者不可读: " + file.getAbsolutePath());
            throw new DemoException("file cannot read: " + file.getAbsolutePath());
        }
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
            return ConnUtil.getInputStreamContent(is);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    private String base64Encode(byte[] content) {

        Base64.Encoder encoder = Base64.getEncoder(); // JDK 1.8  推荐方法
        String str = encoder.encodeToString(content);

        return str;
    }


    public void Character2Audio(String text,String fileName) throws IOException, DemoException, JSONException {
        String url = "http://tsn.baidu.com/text2audio"; // 可以使用https

        TokenHolder holder = new TokenHolder(APP_KEY, SECRET_KEY, TokenHolder.ASR_SCOPE);
        holder.resfresh();
        String token = holder.getToken();
        // 此处2次urlencode， 确保特殊字符被正确编码
        String params = "tex=" + ConnUtil.urlEncode(ConnUtil.urlEncode(text));
        params += "&per=" + per;
        params += "&spd=" + spd;
        params += "&pit=" + pit;
        params += "&vol=" + vol;
        params += "&cuid=" + CUID;
        params += "&tok=" + token;
        params += "&aue=" + aue;
        params += "&lan=zh&ctp=1";
        System.out.println(url + "?" + params); // 反馈请带上此url，浏览器上可以测试
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setConnectTimeout(5000);
        PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
        printWriter.write(params);
        printWriter.close();
        String contentType = conn.getContentType();
        if (contentType.contains("audio/")) {
            byte[] bytes = ConnUtil.getResponseBytes(conn);
            String format = getFormat(aue);
            //File file = new File("result." + format); // 打开mp3文件即可播放
            File file = new File(fileName); // 打开mp3文件即可播放
            // System.out.println( file.getAbsolutePath());
            FileOutputStream os = new FileOutputStream(file);
            os.write(bytes);
            os.close();
            System.out.println("audio file write to " + file.getAbsolutePath());
        } else {
            System.err.println("ERROR: content-type= " + contentType);
            String res = ConnUtil.getResponseString(conn);
            System.err.println(res);
        }
    }
    // 下载的文件格式, 3：mp3(default) 4： pcm-16k 5： pcm-8k 6. wav
    private String getFormat(int aue) {
        String[] formats = {"mp3", "pcm", "pcm", "wav"};
        return formats[aue - 3];
    }
}
