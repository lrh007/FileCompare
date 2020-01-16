import frame.MyFrame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

/**
 * 主程序
 *
 * @ClassName Run
 * @Author lrh
 * @Date 2020/1/14 10:45
 * @Version 1.0
 */
public class Run {

    public static void main(String[] args) throws IOException {
        MyFrame frame = MyFrame.getINSTANCE();

//        long startTime = System.currentTimeMillis();
        /*File file = new File("C:\\Users\\MACHENIKE\\Desktop\\2.txt");
        FileChannel fileChannel = new FileInputStream(file).getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1048576);
        int len = 0;
        while((len = fileChannel.read(byteBuffer))!=-1){
            System.out.println(new String(byteBuffer.array(),0,len,"UTF-8"));
            byteBuffer.clear();
        }
        fileChannel.close();*/

        /*Scanner scanner = new Scanner(file);
        while(scanner.hasNext()){
            System.out.println(scanner.nextLine());
        }
        scanner.close();*/
//        System.out.println("耗时："+(System.currentTimeMillis()-startTime));
    }
}
