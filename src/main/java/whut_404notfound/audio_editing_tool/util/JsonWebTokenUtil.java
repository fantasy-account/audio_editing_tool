package whut_404notfound.audio_editing_tool.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

import java.text.ParseException;

/**
 * 采用对称加密算法HMAC生成jwt的工具类
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/3/17
 */
public class JsonWebTokenUtil {
    /**
     * 由秘钥和payload生成token
     *
     * @param payloadStr payload字符串
     * @param secret 秘钥，至少为32字节
     * @return token字符串
     * @throws JOSEException
     */
    public static String generateTokenByHMAC(String payloadStr, String secret) throws JOSEException {
        //创建JWS头，设置签名算法和类型
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.HS256).
                type(JOSEObjectType.JWT).build();
        //将负载信息封装到Payload中
        Payload payload = new Payload(payloadStr);
        //创建JWS对象
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        //创建HMAC签名器
        JWSSigner jwsSigner = new MACSigner(secret);
        //签名
        jwsObject.sign(jwsSigner);
        return jwsObject.serialize();
    }

    /**
     * 验证token是否合法
     *
     * @param token 被加密的原token字符串
     * @param secret 秘钥
     * @return token是否合法
     * @throws JOSEException
     * @throws ParseException
     */
    public static boolean verifyTokenByHMAC(String token, String secret) throws JOSEException, ParseException {
        //从token中解析JWS对象
        JWSObject jwsObject = JWSObject.parse(token);
        //创建HMAC验证器
        JWSVerifier jwsVerifier = new MACVerifier(secret);
        return jwsObject.verify(jwsVerifier);
    }

    /**
     * 从token中获取payload
     *
     * @param token 被加密的原token字符串
     * @return payload字符串
     * @throws ParseException
     */
    public static String getPayloadFromToken(String token) throws ParseException {
        return JWSObject.parse(token).getPayload().toString();
    }
}
