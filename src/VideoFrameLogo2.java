import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.util.ArrayList;
import java.util.List;

public class VideoFrameLogo2 {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat src = new Mat();
        // open image as is, unchanged, with alpha
        Mat logo = Imgcodecs.imread("./test_images/halloween.png", Imgcodecs.IMREAD_UNCHANGED); // !!!
        Mat logoB = new Mat();
        Mat logoG = new Mat();
        Mat logoR = new Mat();
        Mat logoA = new Mat();

        Mat logoBGR = new Mat();

        Imgproc.resize(logo,logo,new Size(200,200),0.5);

        System.out.println("Size: " + logo.size());
        System.out.println("Type: "+ CvType.typeToString(logo.type()));
        System.out.println("Channels: " + logo.channels());

        // BGRA
        Core.extractChannel(logo,logoB,0);
        Core.extractChannel(logo,logoG,1);
        Core.extractChannel(logo,logoR,2);
        Core.extractChannel(logo,logoA,3);

        // recompose image without alpha
        List<Mat> rgbList = new ArrayList<>();
        rgbList.add(logoB);
        rgbList.add(logoG);
        rgbList.add(logoR);

//        Core.merge(rgbList,logoBGR);

        // or convert from BGRA to BGR without alpha channel
        Imgproc.cvtColor(logo,logoBGR,Imgproc.COLOR_BGRA2BGR);

        VideoCapture capture = new VideoCapture(0);

        if(capture.isOpened()){
            while (true){
                if(!capture.read(src)) break;

                Mat logoROI = src.submat(new Rect(0,0,logo.width(),logo.height()));
//                logoBGR.copyTo(logoROI);
                logoBGR.copyTo(logoROI, logoA); // logo2A = mask
                //or
//                Core.copyTo(logoBGR, logoROI, logoA);

                HighGui.imshow("Src",src);
                HighGui.imshow("LogoROI",logoROI);
                HighGui.imshow("LogoBGR",logoBGR);

                HighGui.imshow("LogoB",logoB);
                HighGui.imshow("LogoG",logoG);
                HighGui.imshow("LogoR",logoR);
                HighGui.imshow("LogoA",logoA);

                int key = HighGui.waitKey(20);
                if(key == 27)
                    break;
            }

        }
        HighGui.destroyAllWindows();
        System.exit(0);
    }
}
