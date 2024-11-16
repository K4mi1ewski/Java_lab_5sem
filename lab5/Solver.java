import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class Solver
{
    private int[][] ImageToArray(BufferedImage p_img)
    {
        int height = p_img.getHeight();
        int width = p_img.getWidth();
        int[][]pixels = new int [height][width];
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                int p = p_img.getRGB(j, i);

                int r = (p>>16) & 0xff;
                int g = (p>>8) & 0xff;
                int b = p & 0xff;

                if (r==0&&g==0&&b==0)
                {
                    pixels[i][j] = 0;
                }
                else
                {
                    pixels[i][j] = 1;
                }
            }
        }
        return pixels;
    }

    public int[][] LoadImage(String p_filename) throws IOException {
        try
        {
            BufferedImage img = ImageIO.read(new File(p_filename));
            if (img == null)
            {
                throw new IOException("Nieprawidłowy format pliku obrazu: " + p_filename);
            }
            return ImageToArray(img);
        }
        catch (IOException e)
        {
            System.err.println("Błąd podczas wczytywania obrazu: " + p_filename);
            throw e;
        }
    }

    private boolean checkIfMatches (int x, int y, int[][] testImage, int [][]refImage)
    {
        int refHeight = refImage.length;
        int refWidth = refImage[0].length;
        int testHeight = testImage.length;
        int testWidth = testImage[0].length;

        for (int i = 0; i<refHeight && (i+x)<testHeight; i++)
        {
            for (int j = 0; j<refWidth && (j+y)<testWidth; j++)
            {
                if (testImage[i+x][j+y] != refImage[i][j])
                    return false;
            }
        }
        return true;
    }

    public ArrayList<Point> GetMagpies(int[][] refImage, int[][] testImage)
    {
        ArrayList<Point> magpieCoords = new ArrayList<>();
        int testHeight = testImage.length;
        int testWidth = testImage[0].length;

        for (int i = 0; i < testHeight; i++)
        {
            for (int j = 0; j < testWidth; j++)
            {
                if (checkIfMatches(i, j, testImage, refImage))
                {
                    magpieCoords.add(new Point(i, j));
                }
            }
        }
        return magpieCoords;
    }

    public int[][] clearImage(int H, int W, int[][] refImage, ArrayList<Point>coords)
    {
        int[][] newImage = new int[H][W];
        int refHeight = refImage.length;
        int refWidth = refImage[0].length;

        for (Point p: coords)
        {
            int x = p.x;
            int y = p.y;
            for (int k = 0; (x+k) < H && k<refHeight; k++)
            {
                for (int l = 0; (y+l) < W && l < refWidth; l++)
                {
                    newImage[k+x][l+y] = refImage[k][l];
                }
            }
        }
        return newImage;
    }

    public BufferedImage displayImage(int[][] pixels)
    {
        var image = new BufferedImage(pixels.length, pixels[0].length, BufferedImage.TYPE_BYTE_GRAY);
        Color white = new Color(255, 255, 255);
        int whiteRGB = white.getRGB();
        int H = pixels.length;
        int W = pixels[0].length;
        for (int i = 0; i < H; i++)
        {
            for (int j = 0; j < W; j++)
            {
                if (pixels[i][j] == 1)
                {
                    image.setRGB(j,i, whiteRGB);
                }
            }
        }
        return image;
    }

    public static void saveImageAsTiff(BufferedImage image, String filename) {
        try {
            File outputFile = new File(filename);
            boolean result = ImageIO.write(image, "TIFF", outputFile);

            if (result) {
                System.out.println("Obraz zapisano jako: " + filename);
            } else {
                System.out.println("Nie udało się zapisać obrazu w formacie TIFF.");
            }
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisywania obrazu: " + e.getMessage());
        }
    }

    public static void main(String[] args)
    {
        Solver solver = new Solver();
        try
        {
            int[][] testImage = solver.LoadImage("testImage.tif");
            int[][] refImage = solver.LoadImage("refImage.tif");
            ArrayList<Point> magpieCoords = solver.GetMagpies(refImage, testImage);
            int[][] clearedPixels = solver.clearImage(testImage.length, testImage[0].length, refImage, magpieCoords);
            BufferedImage clearedImage = solver.displayImage(clearedPixels);
            saveImageAsTiff(clearedImage, "clearedImage.tif");
        }
        catch (IOException e)
        {
            System.err.println("Błąd podczas wczytywania obrazu: " + e.getMessage());
            e.printStackTrace();
        }


    }
}