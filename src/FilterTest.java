import FileIO.PDFHelper;
import Filters.CropFilter;
import Filters.DisplayInfoFilter;
import Interfaces.PixelFilter;
import core.DImage;
import core.DisplayWindow;
import processing.core.PApplet;
import processing.core.PImage;

public class FilterTest {
    public static String currentFolder = System.getProperty("user.dir") + "/";

    public static void main(String[] args) {
        // ----------------------------------------------------------------
        // >>> Run this to save a pdf page and run filters on the image <<<
        // ----------------------------------------------------------------
        SaveAndDisplayExample(1);

        // -------------------------------------------------------------------------------
        // >>> Run this to run your filter on a page /without/ displaying anything <<<
        // -------------------------------------------------------------------------------
        //RunTheFilter();
    }

    private static void RunTheFilter() {
        System.out.println("Loading pdf....");
        PImage in = PDFHelper.getPageImage("assets/testDoc.pdf",1);
        DImage img = new DImage(in);       // you can make a DImage from a PImage

 /*       System.out.println("Running filter on page 1....");
        DisplayInfoFilter filter = new DisplayInfoFilter();
        filter.processImage(img);  // if you want, you can make a different method
                                   // that does the image processing an returns a DTO with
                                   // the information you want*/

    }

    private static void SaveAndDisplayExample(int page) {
        PImage img = PDFHelper.getPageImage("assets/testDocRevd.pdf",page);
        img.resize(img.width/2, img.height/2);
        img.save(currentFolder + "assets/page" + page + ".png");
        DisplayWindow.showFor("assets/page" + page +".png");
    }
}