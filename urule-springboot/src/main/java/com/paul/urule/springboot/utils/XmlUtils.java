package com.paul.urule.springboot.utils;

import com.bstek.urule.RuleException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * @ClassName XmlUtils
 * @Description TODO
 * @Author shuang669539827@163.com
 * @Date2019/8/5 16:08
 * @Version v1.0
 **/
public class XmlUtils {


    /**
     * 解析
     * @param stream
     * @return
     */
    public static Element parseXml(InputStream stream){
        SAXReader reader=new SAXReader();
        Document document;
        try {
            document = reader.read(stream);
            Element root=document.getRootElement();
            return root;
        } catch (DocumentException e) {
            throw new RuleException(e);
        }
    }


}
