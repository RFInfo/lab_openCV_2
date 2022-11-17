import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.videoio.VideoCapture;

public class VideoFrameCapture {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("Hello OpenCv");
    }

    public static void main(String[] args) {

//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat src = new Mat();

        VideoCapture cam = new VideoCapture(0);

        if (!cam.isOpened()) return;

        while (true) {
            cam.read(src);
            if (src.empty()) break;

            HighGui.imshow("Original", src);

            int key = HighGui.waitKey(20);
            if (key == 27)
                break;
        }

        HighGui.destroyAllWindows();
        System.exit(0);
    }
}
