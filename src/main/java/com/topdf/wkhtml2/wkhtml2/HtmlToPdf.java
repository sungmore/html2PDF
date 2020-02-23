package com.topdf.wkhtml2.wkhtml2;


import java.io.File;

public class HtmlToPdf {
    // wkhtmltopdf在系统中的路径
    //windows环境
    private static final String toPdfTool = "D:\\wkhtmltopdf\\bin\\wkhtmltopdf.exe";
    //linux环境
//    private static final String toPdfTool = "//usr//local//bin//wkhtmltopdf";
 
    public static boolean convert(String toPdfTool, String srcPath, String destPath) {
        File file = new File(destPath);
        File parent = file.getParentFile();
        // 如果pdf保存路径不存在，则创建路径
        if (!parent.exists()) {
            parent.mkdirs();
        }
        StringBuilder cmd = new StringBuilder();
        //引入wkhtmltopdf
        cmd.append(toPdfTool);
        cmd.append(" ");
        //设置页面大小
        cmd.append(" --page-size A4");
        //左边间距
        cmd.append(" --margin-left 4");
        // 页眉下面的线
        cmd.append("  --header-line");
        //页眉中间内容
        cmd.append(" --header-center 版权所有 ");
        // (添加一个HTML页眉,后面是网址)
        cmd.append("  --margin-top 2cm ");
        // (设置页眉和内容的距离,默认0)
        cmd.append(" --header-spacing 5 ");
        //设置在中心位置的页脚内容
        cmd.append(" --footer-center 第[page]页／共[topage]页");
        //页脚字体大小
        cmd.append(" --footer-font-size 7");
        // * 显示一条线在页脚内容上)
        cmd.append(" --footer-line");
        // (设置页脚和内容的距离)
        cmd.append(" --footer-spacing 0 ");
        cmd.append(srcPath);
        cmd.append(" ");
        cmd.append(destPath);
        boolean result = true;
        try {
            Process proc = Runtime.getRuntime().exec(cmd.toString());
            HtmlToPdfInterceptor error = new HtmlToPdfInterceptor(proc.getErrorStream());
            HtmlToPdfInterceptor output = new HtmlToPdfInterceptor(proc.getInputStream());
            error.start();
            output.start();
            proc.waitFor();
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
    	long start = System.currentTimeMillis();
        convert(toPdfTool,"https://blog.csdn.net/qq_27512319/article/details/70195690", "d://test//test//wkhtmltopdf.pdf");
        long end = System.currentTimeMillis();
        System.out.println("end:"+end);
        long cost = System.currentTimeMillis()-start;
        System.out.println("cost:"+cost);
    }

}