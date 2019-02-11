package com.bootme.app.util.pdf;

import com.itextpdf.text.DocumentException;
import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * PDF生成工具类
 *
 * @author Goofy <a href="http://www.xdemo.org">http://www.xdemo.org</a>
 */
public class PdfUtils {
    public static void main(String[] args) {
        try {
            Map<Object, Object> o = new HashMap<Object, Object>();
            // o.put("name", "http://www.xdemo.org/");
            // City city = new City(3, "aaa", "bbb", "cccc");
            //model.addAttribute("city", city);
            // o.put("city", city);

            String path = PdfHelper.getPath();
            path = "D:/03Developing/java/SpringBootStrap/webapplication/target/classes/web/";
            generateToFile(path, "city.ftl", path + "resources/", o, "D:\\xdemo.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成PDF到文件
     *
     * @param ftlPath       模板文件路径（不含文件名）
     * @param ftlName       模板文件吗（不含路径）
     * @param imageDiskPath 图片的磁盘路径
     * @param data          数据
     * @param outputFile    目标文件（全路径名称）
     * @throws Exception
     */
    public static void generateToFile(String ftlPath, String ftlName, String imageDiskPath, Object data, String outputFile) throws Exception {
        String html = PdfHelper.getPdfContent(ftlPath, ftlName, data);
        OutputStream out = null;
        ITextRenderer render = null;
        out = new FileOutputStream(outputFile);
        render = PdfHelper.getRender();
        render.setDocumentFromString(html);
        if (imageDiskPath != null && !imageDiskPath.equals("")) {
//html中如果有图片，图片的路径则使用这里设置的路径的相对路径，这个是作为根路径
            render.getSharedContext().setBaseURL("file:/" + imageDiskPath);
        }
        render.layout();
        render.createPDF(out);
        render.finishPDF();
        render = null;
        out.close();
    }
    /**
     * 生成PDF到输出流中（ServletOutputStream用于下载PDF）
     * @param ftlPath ftl模板文件的路径（不含文件名）
     * @param ftlName ftl模板文件的名称（不含路径）
     * @param imageDiskPath 如果PDF中要求图片，那么需要传入图片所在位置的磁盘路径
     * @param data 输入到FTL中的数据
     * @param response HttpServletResponse
     * @return
     * @throws TemplateNotFoundException
     * @throws MalformedTemplateNameException
     * @throws ParseException
     * @throws IOException
     * @throws TemplateException
     * @throws DocumentException

    public static OutputStream generateToServletOutputStream(String ftlPath,String ftlName,String imageDiskPath,Object data,HttpServletResponse response) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException, DocumentException {
    String html=PdfHelper.getPdfContent(ftlPath, ftlName, data);
    OutputStream out = null;
    ITextRenderer render = null;
    out = response.getOutputStream();
    render = PdfHelper.getRender();
    render.setDocumentFromString(html);
    if(imageDiskPath!=null&&!imageDiskPath.equals("")){
    //html中如果有图片，图片的路径则使用这里设置的路径的相对路径，这个是作为根路径
    render.getSharedContext().setBaseURL("file:/"+imageDiskPath);
    }
    render.layout();
    render.createPDF(out);
    render.finishPDF();
    render = null;
    return out;
    }
     */
}
