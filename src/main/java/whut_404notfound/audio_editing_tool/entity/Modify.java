package whut_404notfound.audio_editing_tool.entity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.*;

/**
 * @author Xiaoyu Fan
 * @description 对应数据库中的视频修改记录表
 * @create 2021-03-09 16:38
 */
@Entity
@Data
public class Modify {
    @Id
    private Integer videoId;
    private Integer userId;
    private Integer partNum;
    private Integer modifiedPartNum;//被修改过的视频片段有多少
    private Integer modifiedDuration;//被修改过的总时长是多少
    private VideoPart inclusivePart;//视频片对象，记录每片起始和结束位置
    private VideoPart modifiedPart;//被修改过的视频片对象，记录修改的起始结束位置

    /**
     * 将对象抓换成二进制对象
     * Ps：对象必须实现Serializable接口,最好能给定一个serialVersionUID
     * @param data 对象
     * @return 二进制对象bytes
     */
    public static byte[] toByteArray (VideoPart data)throws IOException {
        ByteArrayOutputStream byt=new ByteArrayOutputStream();
        ObjectOutputStream obj=new ObjectOutputStream(byt);
        obj.writeObject(data);
        byte[] bytes=byt.toByteArray();
        System.out.println("这里是序列化结果"+bytes);
        return bytes;
    }

    /**
     * 将对象抓换成二进制对象
     * Ps：对象必须实现Serializable接口,最好能给定一个serialVersionUID
     * @param bytes
     * @return VideoPart 对象
     */
    public static VideoPart toObject(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteInt=new ByteArrayInputStream(bytes);
        ObjectInputStream objInt=new ObjectInputStream(byteInt);
        VideoPart data;
        data=(VideoPart)objInt.readObject();
        System.out.println("这里是反序列化转化的对象"+data);
        return data;
    }
}
