package com.bootme.app.util.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import freemarker.core.ParseException;
import freemarker.template.*;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;

/**
 * PDF生成辅助类
 *
 * @author Goofy <a href="http://www.xdemo.org">http://www.xdemo.org</a>
 */
@SuppressWarnings("deprecation")
public class PdfHelper {
    public static ITextRenderer getRender() throws DocumentException, IOException {
        ITextRenderer render = new ITextRenderer();
        String path = getPath();
//添加字体，以支持中文
        render.getFontResolver().addFont(path + "/font/ARIALUNI.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        render.getFontResolver().addFont(path + "/font/SIMSUN.TTC", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        //render.getSharedContext().setBaseURL("file:///"+path); //图片地址写死成绝对路径就可以！
        return render;
    }

    //获取要写入PDF的内容
    public static String getPdfContent(String ftlPath, String ftlName, Object o) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
        return useTemplate(ftlPath, ftlName, o);
    }

    //使用freemarker得到html内容
    public static String useTemplate(String ftlPath, String ftlName, Object o) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
        String html = null;
        Template tpl = getFreemarkerConfig(ftlPath).getTemplate(ftlName);
        tpl.setEncoding("UTF-8");
        StringWriter writer = new StringWriter();
        tpl.process(o, writer);
        writer.flush();
        html = writer.toString();
        return html;
    }

    /**
     * 获取Freemarker配置
     *
     * @param templatePath
     * @return
     * @throws IOException
     */
    private static Configuration getFreemarkerConfig(String templatePath) throws IOException {
        Configuration config = new Configuration();
        config.setDirectoryForTemplateLoading(new File(templatePath));
        config.setEncoding(Locale.CHINA, "utf-8");
        return config;
    }

    /**
     * 获取类路径
     *
     * @return
     */
    public static String getPath() {
        return "D:/03Developing/java/SpringBootStrap/webapplication/target/classes/web";
        //    return PdfHelper.class.getResource("").getPath().substring(1);
    }
}