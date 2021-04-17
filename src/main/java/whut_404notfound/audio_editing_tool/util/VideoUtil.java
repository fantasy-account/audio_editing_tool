package whut_404notfound.audio_editing_tool.util;

/**
 * @author Xin Xie
 * @description 视频处理服务
 * @create 2021-03-18 17:07
 */
/*
* 视频音频合并convetor(final String videoInputPath, final String audioInputPath, final String videoOutPath)
 * 音频文件转wav格式 changeFileToWav(final String audioInputPath, final String audioOutputPath)
 * Wav音频文件转pcm格式changeWavToPcm(final String audioInputPath, final String audioOutputPath)
 * 按频率视频切割mp4ToCut1(String videoInputPath, String videoOutputPath,String frequencyTime)
 * 视频切割，精准切割，按视频段的开始时间，和起始时间切割mp4ToCut2(String videoInputPath, String videoOutputPath,String startTime,String endTime)
 * 音频合并mp3ToUnit(final String audioInputPath1, final String audioInputPath2,final String audioOutputPath)
 * 视频格式转ts格式changeToTs(final String videoInputPath,final String videoOutputPath)
 * 视频合并mp4ToUnit(final String videoInputPath1, final String videoInputPath2,final String videoOutputPath)
 * 视频提取I帧changeToJpg(final String videoInputPath,final String jpgOutputPath,int minute,int second)
 * 获取视频总时间getVideoTime(final String video_path)
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 视频中获取音频文件
 */
public class VideoUtil {
    //FFmpeg全路径
    private static final String FFMPEG_PATH = "ffmpeg";

    /**
     * 从视频中提取音频信息
     *
     * @param audioInputPath
     * @param videoOutputPath
     * @return
     */
    public static String videoToAudio(final String audioInputPath, final String videoOutputPath) {
        String aacFile = "";
        try {
            aacFile = audioInputPath + "/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                    + UUID.randomUUID().toString().replaceAll("-", "") + ".mp3";
            final String command = FFMPEG_PATH + " -i " + videoOutputPath + " " + aacFile;
            System.out.println("video to audio command : " + command);
            final Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 视频音频合并
     *
     * @param videoInputPath 原视频的全路径
     * @param audioInputPath 音频的全路径
     * @param videoOutPath   视频与音频结合之后的视频的路径
     * @throws Exception
     */
    public static void convetor(final String videoInputPath, final String audioInputPath, final String videoOutPath)
            throws Exception {
        Process process = null;
        try {
            final String command = FFMPEG_PATH + " -i " + videoInputPath + " -i " + audioInputPath + " -c:v copy -c:a aac -strict experimental " +
                    " -map 0:v:0 -map 1:a:0 "
                    + " -y " + videoOutPath;
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
        final InputStream errorStream = process.getErrorStream();
        final InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        final BufferedReader br = new BufferedReader(inputStreamReader);
        String line = "";
        while ((line = br.readLine()) != null) {
        }
        if (br != null) {
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }
    }

    /**
     * 音频文件转wav格式
     * @param fileInputPath
     * @param audioOutputPath
     * @return
     * @throws Exception
     */
    public static void changeFileToWav(final String fileInputPath, final String audioOutputPath) throws Exception {
        Process process = null;
        try {
            //执行ffmpeg.exe,前面是ffmpeg.exe的地址，中间是需要转换的文件地址，后面是转换后的文件地址。-i是转换方式，意思是可编码解码，mp3编码方式采用的是libmp3lame
            //wav转pcm
            //Process p=run.exec(new File(webroot).getAbsolutePath()+"/ffmpeg -y -i "+sourcePath+" -acodec pcm_s16le -f s16le -ac 1 -ar 16000 "+targetPath);
            //mp3转pcm
            // Process p = run.exec(new File(webroot).getAbsolutePath() + "/ffmpeg -y -i " + sourcePath + " -acodec pcm_s16le -f s16le -ac 1 -ar 16000 " + targetPath);
            //16bit 单声道 48khz
           // final String command = FFMPEG_PATH + " -i " + fileInputPath + " -ar 48000 -ac 1 -acodec pcm_s16le " + audioOutputPath;
            //16bit 单声道 16khz
            final String command =FFMPEG_PATH + " -i " + fileInputPath + " -ac 1 -ar 16000 -ab 16 " + audioOutputPath;
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
        final InputStream errorStream = process.getErrorStream();
        final InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        final BufferedReader br = new BufferedReader(inputStreamReader);
        String line = "";
        while ((line = br.readLine()) != null) {
        }
        if (br != null) {
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }
    }
    /**
     * 多个视频合并
     * @param videoInputPath
     * @param videoOutputPath
     * @return
     * @throws Exception
     */
    public static Boolean mp4ToUnit2(final String[] videoInputPath,final String videoOutputPath) throws Exception {
        Process process =null;
        try {
            for(int i=0;i<videoInputPath.length;i++)
            {
                int beginIndex=videoInputPath[i].length()-3;
                int endIndex=videoInputPath[i].length();
                videoInputPath[i]=videoInputPath[i].substring(0, beginIndex)+"ts";
            }
            String video="";
            for(int i=0;i<videoInputPath.length;i++)
            {
                video+=videoInputPath[i]+"|";
//
//                video.concat(videoInputPath[i]);
//                video.concat("|");
            }
            System.out.println(video);
            final String command =FFMPEG_PATH + " -i \"concat:" + video+"\" -c copy -bsf:a aac_adtstoasc -movflags +faststart "+videoOutputPath;
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
        }catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        // 使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
        final InputStream errorStream = process.getErrorStream();
        final InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        final BufferedReader br = new BufferedReader(inputStreamReader);
        String line = "";
        while ((line = br.readLine()) != null) {
        }
        if (br != null) {
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }
        return true;
    }
    /**
     * 多个视频格式转ts
     * @param videoInputPath
     * @return
     * @throws Exception
     */
    public static void changeToTs2(final String[] videoInputPath) throws Exception {
        for(int i=0;i<videoInputPath.length;i++)
        {
            int beginIndex=videoInputPath[i].length()-3;
            //int endIndex=videoInputPath[i].length();
            String videoOutputPath=videoInputPath[i].substring(0, beginIndex)+"ts";
            System.out.println(videoOutputPath);
            changeToTs(videoInputPath[i],videoOutputPath);
        }

    }


    /**
     * Wav音频文件转pcm格式
     *
     * @param audioInputPath
     * @param audioOutputPath
     * @return
     * @throws Exception
     */
    public static void changeWavToPcm(final String audioInputPath, final String audioOutputPath) throws Exception {
        Process process = null;
        try {
            final String command = FFMPEG_PATH + " -y -i " + audioInputPath + " -acodec pcm_s16le -f s16le -ac 1 -ar 16000 " + audioOutputPath;
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
        final InputStream errorStream = process.getErrorStream();
        final InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        final BufferedReader br = new BufferedReader(inputStreamReader);
        String line = "";
        while ((line = br.readLine()) != null) {
        }
        if (br != null) {
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }
    }

    /**
     * 视频切割
     *
     * @param videoInputPath
     * @param videoOutputPath
     * @param frequencyTime
     * @return
     * @throws Exception
     */
    public static void mp4ToCut1(String videoInputPath, String videoOutputPath, String frequencyTime) throws Exception {
        Process process = null;
        try {
            final String command = FFMPEG_PATH + " -i " + videoInputPath + " -f segment -segment_time " + frequencyTime + " -c copy " + videoOutputPath + "%02d.mp4";
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
        final InputStream errorStream = process.getErrorStream();
        final InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        final BufferedReader br = new BufferedReader(inputStreamReader);
        String line = "";
        while ((line = br.readLine()) != null) {
        }
        if (br != null) {
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }
    }

    /**
     * 视频切割
     * @param videoInputPath
     * @param videoOutputPath
     * @param FrequencyTime
     * @return
     * @throws Exception
     */
    public static void mp4ToCut3(String videoInputPath, String videoOutputPath,int FrequencyTime) throws Exception {
        int time=getVideoTime(videoInputPath);
        int n=time/FrequencyTime;
        int m=0;
        String f=Integer.toString(FrequencyTime);
        for(int i=0;i<n;i++)
        {
            String a=Integer.toString(m);
            String b=null;
            if(i<=9){
                b="0"+i;
            }else{
                b=i+"";
            }
            mp4ToCut2(videoInputPath, videoOutputPath+"\\"+b+".mp4",a,f);
            m+=FrequencyTime;
        }
            	/*
            	String c=Integer.toString(n*FrequencyTime);
            	String d=Integer.toString(time-n*FrequencyTime);
            	mp4ToCut2(videoInputPath, videoOutputPath+"\\audio"+(n+1)+".mp4",c,d);
            	*/
    }


    /**
     * 视频切割，精准切割，按视频段的开始时间，和时间长度切割
     * @param videoInputPath
     * @param videoOutputPath
     * @param startTime
     * @param FrequencyTime
     * @return
     * @throws Exception
     */
    public static void mp4ToCut2(String videoInputPath, String videoOutputPath,String startTime,String FrequencyTime) throws Exception {
        Process process = null;
        try {
            final String command =FFMPEG_PATH + " -ss "+startTime+" -t "+FrequencyTime + " -accurate_seek -i " + videoInputPath + " -codec copy -avoid_negative_ts 1 " + videoOutputPath;
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
        }catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
        final InputStream errorStream = process.getErrorStream();
        final InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        final BufferedReader br = new BufferedReader(inputStreamReader);
        String line = "";
        while ((line = br.readLine()) != null) {
        }
        if (br != null) {
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }
    }


    /**
     * 音频合并
     *
     * @param audioInputPath1
     * @param audioInputPath2
     * @param audioOutputPath
     * @return
     * @throws Exception
     */
    public static void mp3ToUnit(final String audioInputPath1, final String audioInputPath2, final String audioOutputPath) throws Exception {
        Process process = null;
        try {
            final String command = FFMPEG_PATH + " -i concat:" + audioInputPath1 + "|" + audioInputPath2 + " -acodec copy " + audioOutputPath;
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
        final InputStream errorStream = process.getErrorStream();
        final InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        final BufferedReader br = new BufferedReader(inputStreamReader);
        String line = "";
        while ((line = br.readLine()) != null) {
        }
        if (br != null) {
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }
    }

    /**
     * 视频格式转ts
     *
     * @param videoInputPath
     * @param videoOutputPath
     * @return
     * @throws Exception
     */
    public static void changeToTs(final String videoInputPath, final String videoOutputPath) throws Exception {
        Process process = null;
        try {
            //执行ffmpeg.exe,前面是ffmpeg.exe的地址，中间是需要转换的文件地址，后面是转换后的文件地址。-i是转换方式，意思是可编码解码，mp3编码方式采用的是libmp3lame
            //wav转pcm
            //Process p=run.exec(new File(webroot).getAbsolutePath()+"/ffmpeg -y -i "+sourcePath+" -acodec pcm_s16le -f s16le -ac 1 -ar 16000 "+targetPath);
            //mp3转pcm
            // Process p = run.exec(new File(webroot).getAbsolutePath() + "/ffmpeg -y -i " + sourcePath + " -acodec pcm_s16le -f s16le -ac 1 -ar 16000 " + targetPath);
            //16bit 单声道 48khz
            final String command = FFMPEG_PATH + " -i " + videoInputPath + " -c copy -bsf:v h264_mp4toannexb -f mpegts " + videoOutputPath;
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
        final InputStream errorStream = process.getErrorStream();
        final InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        final BufferedReader br = new BufferedReader(inputStreamReader);
        String line = "";
        while ((line = br.readLine()) != null) {
        }
        if (br != null) {
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }
    }

    /**
     * 视频合并
     *
     * @param videoInputPath1
     * @param videoInputPath2
     * @param videoOutputPath
     * @return
     * @throws Exception
     */
    public static void mp4ToUnit(final String videoInputPath1, final String videoInputPath2, final String videoOutputPath) throws Exception {
        Process process = null;
        try {
            final String command = FFMPEG_PATH + " -i \"concat:" + videoInputPath1 + "|" + videoInputPath2 + "\" -c copy -bsf:a aac_adtstoasc -movflags +faststart " + videoOutputPath;
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
        final InputStream errorStream = process.getErrorStream();
        final InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        final BufferedReader br = new BufferedReader(inputStreamReader);
        String line = "";
        while ((line = br.readLine()) != null) {
        }
        if (br != null) {
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }
    }

    /**
     * 视频提取I帧
     *
     * @param videoInputPath
     * @param jpgOutputPath
     * @param minute
     * @param second
     * @return
     * @throws Exception
     */
    public static void changeToJpg(final String videoInputPath, final String jpgOutputPath, int minute, int second) throws Exception {
        Process process = null;
        try {
            final String command = FFMPEG_PATH + " -i " + videoInputPath + " -ss 00:" + minute + ":" + second + " -t 1 -r 1 -q:v 2 -f image2 " + jpgOutputPath;
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
        final InputStream errorStream = process.getErrorStream();
        final InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        final BufferedReader br = new BufferedReader(inputStreamReader);
        String line = "";
        while ((line = br.readLine()) != null) {
        }
        if (br != null) {
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }
    }


    // 格式:"00:00:10.68"
    private static int getTimelen(String timelen) {
        int min = 0;
        String strs[] = timelen.split(":");
        if (strs[0].compareTo("0") > 0) {
            // 秒
            min += Integer.valueOf(strs[0]) * 60 * 60;
        }
        if (strs[1].compareTo("0") > 0) {
            min += Integer.valueOf(strs[1]) * 60;
        }
        if (strs[2].compareTo("0") > 0) {
            min += Math.round(Float.valueOf(strs[2]));
        }
        return min;
    }

    /**
     * 获取视频总时间
     * *@param videoInputPath
     * @return
     * @throws Exception
     */
    public static Integer getVideoTime(final String video_path) {
        List<String> commands = new ArrayList<>();
        commands.add("ffmpeg");
        commands.add("-i");
        commands.add(video_path);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            Process p = builder.start();

            //从输入流中读取视频信息
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }
            br.close();

            //从视频信息中解析时长
            String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
            Pattern pattern = Pattern.compile(regexDuration);
            Matcher m = pattern.matcher(stringBuilder.toString());
            if (m.find()) {
                int time = getTimelen(m.group(1));
                System.out.println("视频时长：" + time + "s");
                return time;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



//    public static void main(final String[] args){
//        try {
////		videoToAudio("D:\\Eclipse\\laji\\bb.mp4");
////		changeFileToWav("D:\\Eclipse\\666\\c.mp3", "D:\\Eclipse\\666\\c.wav");
////		changeWavToPcm("D:\\Eclipse\\666\\c.wav", "D:\\Eclipse\\666\\c.pcm");
////		changeToJpg("D:\\Eclipse\\666\\a.mp4", "D:\\Eclipse\\666\\a.jpeg", 0,3);
////		mp4ToCutting("D:\\Eclipse\\laji\\bb.mp4", "D:\\Eclipse\\666\\a1.mp4","60");
//            for(int i =60;i<70;i++)
//            {
//                String a =Integer.toString(i);
//                String b =Integer.toString(i+1);
//                mp4ToCut2("D:\\Eclipse\\laji\\bb.mp4", "D:\\Eclipse\\666\\c"+a+".wav",a,b);
//                changeWavToPcm("D:\\Eclipse\\666\\c"+a+".wav", "D:\\Eclipse\\666\\c"+a+".pcm");
//
//            }
////		mp4ToCut2("D:\\Eclipse\\laji\\bb.mp4", "D:\\Eclipse\\666\\c.mp3","60","70");
////		mp3ToUnit("D:\\Eclipse\\666\\audio00.mp3","D:\\Eclipse\\666\\audio07.mp3","D:\\Eclipse\\666\\a.mp3");
////        changeToTs("D:\\Eclipse\\666\\audio01.mp4","D:\\Eclipse\\666\\audio01.ts");
////        changeToTs("D:\\Eclipse\\666\\audio03.mp4","D:\\Eclipse\\666\\audio03.ts");
////		mp4ToUnit("D:\\Eclipse\\666\\audio01.ts","D:\\Eclipse\\666\\audio03.ts","D:\\Eclipse\\666\\a2.mp4");
////		final String videoInputPath = "D:\\Eclipse\\laji\\2.mp4";
////		final String audioInputPath = "D:\\Eclipse\\666\\a.mp3";
////		final String videoOutPath = "D:\\Eclipse\\laji\\bb2.mp4";
////		convetor(videoInputPath,audioInputPath,videoOutPath);
//        } catch (final Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("---------获取音频文件成功！-----------");
//    }

}

