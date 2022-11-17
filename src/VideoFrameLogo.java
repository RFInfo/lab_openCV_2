import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class VideoFrameLogo {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("Hello from static");
    }

    public static void main(String[] args) {

//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat src = new Mat();
        Mat logo = Imgcodecs.imread("./test_images/logo.png");
        Mat logoMask = new Mat();
        Mat background = new Mat(logo.size(), CvType.CV_8UC3, new Scalar(255, 0, 0));

        Core.extractChannel(logo, logoMask, 1);
        Core.bitwise_not(logoMask, logoMask);

        VideoCapture cam = new VideoCapture(0);

        if (cam.isOpened()) {
            while (true) {
                cam.read(src);
                if (src.empty()) break;

                Mat logoROI = src.submat(new Rect(0, 0, logo.width(), logo.height()));

//                Core.copyTo(logo,logoROI, logoMask);
                logo.copyTo(logoROI,logoMask);
//                background.copyTo(logoROI, logoMask);

                HighGui.imshow("Original", src);
                HighGui.imshow("Mask1", logoMask);
                HighGui.imshow("ROI", logoROI);
                HighGui.imshow("Background", background);

                int key = HighGui.waitKey(20);
                if (key == 27)
                    break;
            }
        }

        HighGui.destroyAllWindows();
        System.exit(0);
    }
}
