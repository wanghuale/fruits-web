import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import com.rogrand.kkmy.util.FFmpegUtils;
import com.rogrand.speex.code.JSpeexDec;

// 批量修改文件类型
public class Convent {

    static {
        // 初始化 ffmpeg 工具路径
        FFmpegUtils.FFMPEG = "D:\\Program Files\\ffmpeg-win64-static\\bin\\ffmpeg";
    }

    private static final String url = "D:/Newfolder";

    public static void main(String[] args) throws Exception {
        if (args != null && args.length > 0) {
            if ("mp3".equalsIgnoreCase(args[0])) {
                // Spx 文件转换为 Wav
                spx2wav();

                // Wav 文件转换为 Mp3
                wav2mp3();
            } else if ("wav".equalsIgnoreCase(args[0])) {
                // Spx 文件转换为 Wav
                spx2wav();
            } else if ("mp3".equalsIgnoreCase(args[0])) {
                // Wav 文件转换为 Mp3
                wav2mp3();
            }
        } else {
            // Spx 文件转换为 Wav
            spx2wav();

            // Wav 文件转换为 Mp3
            wav2mp3();
        }
    }

    private static void spx2wav() throws IOException {
        File dir = new File(url);

        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".spx");
            }
        };

        File[] files = dir.listFiles(filter);
        for (int i = 0; i < files.length; i++) {
            String name = files[i].getName();
            String newName = name.substring(0, name.indexOf(".spx")) + ".wav";
            System.out.println(name);
            System.out.println(newName);

            if (new File(url + "/" + newName).exists())
                continue;

            JSpeexDec dec = new JSpeexDec();
            dec.decode(new File(url + "/" + name), new File(url + "/" + newName));
        }
    }

    /**
     * 描述：〈wav2mp3〉 <br/>
     * 作者：xuan.zhou@rogrand.com <br/>
     * 生成日期：2014-1-26 <br/>
     * 
     * @throws InterruptedException
     */
    private static void wav2mp3() throws InterruptedException {
        File dir = new File(url);

        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".wav");
            }
        };

        File[] files = dir.listFiles(filter);
        for (File file : files) {
            String name = file.getName();
            String newName = name.substring(0, name.lastIndexOf(".")) + ".mp3";

            if (new File(url + "/" + newName).exists())
                continue;

            if (!FFmpegUtils.convertWavToMp3(url + "/" + name, url + "/" + newName))
                return;

            Thread.sleep(500l);
        }
    }
}
