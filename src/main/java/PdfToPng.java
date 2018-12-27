import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PdfToPng {
    @Option(name = "-d", aliases = "--dir", usage = "PDFがあるディレクトリを指定します。")
    static String dirName = ".";

    @Option(name = "-r", aliases = "--recursive", usage = "再帰的に実行します。")
    static boolean isRecursive = false;

    @Option(name = "--dpi", usage = "解像度を指定します。")
    static int dpi = 300;

    @Option(name = "-h", aliases = "--help", usage = "このヘルプを表示します。")
    static boolean isHelp = false;

    @Option(name = "-f", aliases = "--filename", usage = "PNG化したいファイルをひとつだけ指定します。\n" +
            "-d オプションと一緒に指定した場合こちらが優先されます。")
    static String filename = null;

    public static void main(String[] args) throws IOException {
        PdfToPng app = new PdfToPng();
        CmdLineParser parser = new CmdLineParser(app);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            isHelp = true;
            System.err.println(e);
        }

        if (filename != null && !filename.toLowerCase().endsWith(".pdf")) {
            System.err.println("ファイル指定:" + filename + "の誤り： ファイル名は PDF でなければなりません。");
            System.exit(-1);
        }

        if (isHelp) {
            System.err.println("Usage: " + new Object(){}.getClass().getEnclosingClass().getName());
            parser.printUsage(System.err);
            System.exit(-1);
        }

        if (filename != null) {
            pdfToPng(new File(filename));
        } else {
            doPdfToPngRecursive(new File(dirName));
        }
    }

    private static void doPdfToPngRecursive(File dir) throws IOException {
        for (File file : dir.listFiles()) {
            if (file.isDirectory() && isRecursive) {
                doPdfToPngRecursive(file);
            } else if (file.isFile() && file.getName().toLowerCase().endsWith(".pdf")) {
                pdfToPng(file);
            }

        }

    }

    // reference: https://stackoverflow.com/questions/23326562/apache-pdfbox-convert-pdf-to-images
    private static void pdfToPng(File file) throws IOException {
        try (PDDocument doc = PDDocument.load(file)) {
            PDFRenderer renderer = new PDFRenderer(doc);
            for (int page = 0 ; page < doc.getNumberOfPages() ; page++) {
                BufferedImage bim = renderer.renderImageWithDPI(page, dpi, ImageType.RGB);
                String basename = file.getName();
                String woext = basename.substring(0, basename.lastIndexOf('.'));
                String pngFilename = String.format("%s/%s-%d.png", file.getParent(), woext, page + 1);
                ImageIOUtil.writeImage(bim, pngFilename, dpi);
            }
        }
    }
}
