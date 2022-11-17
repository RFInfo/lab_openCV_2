import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class VideoFrameProcess {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("Hello OpenCv");
    }

    public static void main(String[] args) {

//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat src = new Mat();
        Mat grayFrame = new Mat();
        Mat thresholdFrame = new Mat();
        Mat blurFrame = new Mat();

        VideoCapture cam = new VideoCapture(0);

        if (!cam.isOpened()) return;

        while (true) {
            cam.read(src);
            if (src.empty()) break;

            Imgproc.cvtColor(src, grayFrame, Imgproc.COLOR_BGR2GRAY);
            Imgproc.blur(grayFrame, blurFrame, new Size(5, 5));
            Imgproc.threshold(blurFrame, thresholdFrame, 250, 255, Imgproc.THRESH_BINARY);

            HighGui.imshow("Original", src);
            HighGui.imshow("Gray", grayFrame);
            HighGui.imshow("Threshold", thresholdFrame);
            HighGui.imshow("Blur", blurFrame);

            int key = HighGui.waitKey(20);
            if (key == 27) {
                break;
            }
        }

        HighGui.destroyAllWindows();
        System.exit(0);
    }
}
