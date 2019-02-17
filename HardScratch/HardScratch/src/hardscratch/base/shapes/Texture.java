package hardscratch.base.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWImage.Buffer;
import static org.lwjgl.opengl.GL11.*;

public class Texture {
    
    private int id, width, height;
    
    public Texture(String fileName){
        BufferedImage buffer;
        try {
            buffer = ImageIO.read(new File(fileName));
            load(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Texture(BufferedImage buffer, int row, int column, int width, int height){
        buffer = buffer.getSubimage(column*width,row*height,width,height);
        load(buffer);
    }
    
    private void load(BufferedImage buffer){
        width = buffer.getWidth();
        height = buffer.getHeight();

        int[] pixels_raw = new int[width * height];
        buffer.getRGB(0, 0, width, height, pixels_raw, 0, width);

        ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                int pixel = pixels_raw[i*width + j];
                pixels.put((byte) ((pixel >> 16) & 0xFF));  // Red
                pixels.put((byte) ((pixel >> 8) & 0xFF));   // Green
                pixels.put((byte) (pixel & 0xFF));          // Blue
                pixels.put((byte) ((pixel >> 24) & 0xFF));  // Alpha
            }
        }
        pixels.flip();

        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
    }
    
    public void bind(){
        glBindTexture(GL_TEXTURE_2D, id);
    }
    
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public int getID(){
        return id;
    }
    
    
    public static Buffer lwjglIsStupid(String fileName){
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File(fileName));
            ByteBuffer imageBuffer;
            WritableRaster raster;
            BufferedImage texImage;

            ColorModel glAlphaColorModel = new ComponentColorModel(ColorSpace
                    .getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 8 },
                    true, false, Transparency.TRANSLUCENT, DataBuffer.TYPE_BYTE);

            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,
                    bufferedImage.getWidth(), bufferedImage.getHeight(), 4, null);
            texImage = new BufferedImage(glAlphaColorModel, raster, true,
                    new Hashtable());

            // copy the source image into the produced image
            Graphics g = texImage.getGraphics();
            g.setColor(new Color(0f, 0f, 0f, 0f));
            g.fillRect(0, 0, 256, 256);
            g.drawImage(bufferedImage, 0, 0, null);

            // build a byte buffer from the temporary image
            // that be used by OpenGL to produce a texture.
            byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer())
                    .getData();

            imageBuffer = ByteBuffer.allocateDirect(data.length);
            imageBuffer.order(ByteOrder.nativeOrder());
            imageBuffer.put(data, 0, data.length);
            imageBuffer.flip();

            GLFWImage image = GLFWImage.malloc();
            image.set(bufferedImage.getWidth(), bufferedImage.getHeight(), imageBuffer);

            GLFWImage.Buffer images = GLFWImage.malloc(1);
            images.put(0, image);
            
            return images;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
