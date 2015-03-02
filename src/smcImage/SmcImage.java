package smcImage;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.jpeg.exifRewrite.ExifRewriter;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.constants.ExifTagConstants;
import org.apache.sanselan.formats.tiff.constants.TagInfo;
import org.apache.sanselan.formats.tiff.constants.TiffConstants;
import org.apache.sanselan.formats.tiff.constants.TiffFieldTypeConstants;
import org.apache.sanselan.formats.tiff.write.TiffOutputDirectory;
import org.apache.sanselan.formats.tiff.write.TiffOutputField;
import org.apache.sanselan.formats.tiff.write.TiffOutputSet;


/**
 *
 * @author presha
 */
public class SmcImage {
   
    public static File openSmcWithOutSmell(File smcFile){
        
        File temp = null;
        try{
            temp = File.createTempFile(smcFile.getName(), ".jpg");
            System.out.println("temp created");
            FileUtils.copyFile(smcFile, temp);
            System.out.println(temp.getAbsoluteFile());
    
            }
        catch(Exception e){}
        return temp;
    }
    
    public static File openSmcWithOutSmell(String smcFile){
        File file = new File(smcFile);
        return openSmcWithOutSmell(file);
    }
    
    public static File saveSmc(File Smcfile, String smcId, String parentFileName) throws ImageReadException, IOException {
                
               if((Smcfile.getName().endsWith("gif"))||(Smcfile.getName().endsWith("png")) ){
               Smcfile = returnJpeg(Smcfile);
               }
               
               else{
               Smcfile = copyJpeg(Smcfile);
               }
                
                IImageMetadata metadata = Sanselan.getMetadata(Smcfile);
                
                File file2 = null;
                TiffOutputSet outputSet = new TiffOutputSet();
                try {
                        TiffOutputDirectory exif = outputSet.getOrCreateExifDirectory();
                        addToTiffField(exif,ExifTagConstants.EXIF_TAG_USER_COMMENT, smcId);
                        BufferedOutputStream os = null;
                        file2 = new File(parentFileName, chopFileName(Smcfile.getName()) + ".smc");
                        try {
                                os = new BufferedOutputStream(new FileOutputStream(file2));
                        } catch (IOException e) {
                                e.printStackTrace();
                        }

                        try {
                                new ExifRewriter().updateExifMetadataLossless(Smcfile, os, outputSet);
                        } catch (ImageReadException e) {
                                e.printStackTrace();
                        } catch (ImageWriteException e) {
                                e.printStackTrace();
                        } catch (IOException e) {
                                e.printStackTrace();
                        } finally {
                                if (os != null) {
                                        try {
                                                os.close();
                                        } catch (IOException e) {
                                        }
                                }
                        }

                } catch (ImageWriteException e) {
                        e.printStackTrace();
                }
                Smcfile.delete();
                return file2;
        }

    public static File saveSmc(File Smcfile, String smcId, File parentFile) throws ImageReadException, IOException {
    return saveSmc(Smcfile,smcId,parentFile.getName());
    }

    public static File editSmc(File Smcfile, String smcId) throws ImageReadException, IOException {
                String parent = Smcfile.getParent();
                Smcfile = returnJpeg(Smcfile);
               
                File file2 = null;
                TiffOutputSet outputSet = new TiffOutputSet();
                try {
                        TiffOutputDirectory exif = outputSet.getOrCreateExifDirectory();
                        addToTiffField(exif,ExifTagConstants.EXIF_TAG_USER_COMMENT, smcId);
                        BufferedOutputStream os = null;
                        file2 = new File(parent, chopFileName(Smcfile.getName()) + ".smc");
                        try {
                                os = new BufferedOutputStream(new FileOutputStream(file2));
                        } catch (IOException e) {
                                e.printStackTrace();
                        }

                        try {
                                new ExifRewriter().updateExifMetadataLossless(Smcfile, os, outputSet);
                        } catch (ImageReadException e) {
                                e.printStackTrace();
                        } catch (ImageWriteException e) {
                                e.printStackTrace();
                        } catch (IOException e) {
                                e.printStackTrace();
                        } finally {
                                if (os != null) {
                                        try {
                                                os.close();
                                        } catch (IOException e) {
                                        }
                                }
                        }

                } catch (ImageWriteException e) {
                        e.printStackTrace();
                }
                Smcfile.delete();
                return file2;
        }

    public static File removeSmc(File Smcfile) throws IOException, ImageReadException{
                
               if((Smcfile.getName().endsWith("gif"))||(Smcfile.getName().endsWith("png")) ){
               Smcfile = returnJpeg(Smcfile);
               }
               
               else{
               Smcfile = copyJpeg(Smcfile);
               }
        
                IImageMetadata metadata = Sanselan.getMetadata(Smcfile);
                
                File file2 = null;
                TiffOutputSet outputSet = new TiffOutputSet();
                try {
                        TiffOutputDirectory exif = outputSet.getOrCreateExifDirectory();
                        addToTiffField(exif,ExifTagConstants.EXIF_TAG_USER_COMMENT, "");
                        BufferedOutputStream os = null;
                        file2 = new File(Smcfile.getParent(), chopFileName(Smcfile.getName()) + ".jpg");
                        try {
                                os = new BufferedOutputStream(new FileOutputStream(file2));
                        } catch (IOException e) {
                                e.printStackTrace();
                        }

                        try {
                                new ExifRewriter().updateExifMetadataLossless(Smcfile, os, outputSet);
                        } catch (ImageReadException e) {
                                e.printStackTrace();
                        } catch (ImageWriteException e) {
                                e.printStackTrace();
                        } catch (IOException e) {
                                e.printStackTrace();
                        } finally {
                                if (os != null) {
                                        try {
                                                os.close();
                                        } catch (IOException e) {
                                        }
                                }
                        }

                } catch (ImageWriteException e) {
                        e.printStackTrace();
                }
                return file2;

}    

    public static File removeSmc(File Smcfile,String destinationFolder) throws IOException, ImageReadException{
               if((Smcfile.getName().endsWith("gif"))||(Smcfile.getName().endsWith("png")) ){
               Smcfile = returnJpeg(Smcfile);
               }
               
               else{
               Smcfile = copyJpeg(Smcfile);
               }
                
                IImageMetadata metadata = Sanselan.getMetadata(Smcfile);
                
                File file2 = null;
                TiffOutputSet outputSet = new TiffOutputSet();
                try {
                        TiffOutputDirectory exif = outputSet.getOrCreateExifDirectory();
                        addToTiffField(exif,ExifTagConstants.EXIF_TAG_USER_COMMENT, "");
                        BufferedOutputStream os = null;
                        file2 = new File(destinationFolder, chopFileName(Smcfile.getName()) + ".jpg");
                        try {
                                os = new BufferedOutputStream(new FileOutputStream(file2));
                        } catch (IOException e) {
                                e.printStackTrace();
                        }

                        try {
                                new ExifRewriter().updateExifMetadataLossless(Smcfile, os, outputSet);
                        } catch (ImageReadException e) {
                                e.printStackTrace();
                        } catch (ImageWriteException e) {
                                e.printStackTrace();
                        } catch (IOException e) {
                                e.printStackTrace();
                        } finally {
                                if (os != null) {
                                        try {
                                                os.close();
                                        } catch (IOException e) {
                                        }
                                }
                        }

                } catch (ImageWriteException e) {
                        e.printStackTrace();
                }
                return file2;

}  

    public static File removeSmc(File Smcfile,File destinationFolder) throws IOException, ImageReadException{
        return removeSmc(Smcfile,destinationFolder.getName());
}
    
    public static String getSmellFromSmc(File Smcfile) throws ImageReadException, IOException{
        IImageMetadata metadata = Sanselan.getMetadata(Smcfile);
        String Smell;
        JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
        TiffField field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_USER_COMMENT);
        Smell = field.getValueDescription();
    return Smell;
    }
    
    private static void addToTiffField(TiffOutputDirectory exif, TagInfo taginfo, String fieldData) {
                TiffOutputField imageHis = new TiffOutputField(taginfo, TiffFieldTypeConstants.FIELD_TYPE_ASCII, fieldData.length(), fieldData.getBytes());
                exif.add(imageHis);
        }
    
    private static String chopFileName(String Filename){
           int DotIndex = Filename.indexOf('.');
           String Name = Filename.substring(0, DotIndex); 
       
       
       return Name;
       }

    private static File returnJpeg(File Smcfile) throws IOException{
                BufferedImage bufferedImage;
                bufferedImage = ImageIO.read(Smcfile);

                    BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
                    newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
                    File f = new File(chopFileName(Smcfile.getName())+".jpg");
                    
                  ImageIO.write(newBufferedImage, "jpg", f);
                
                return f;

}
    
    private static File copyJpeg(File Smcfile) throws IOException{
                BufferedImage bufferedImage;
                bufferedImage = ImageIO.read(Smcfile);

                    BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
                    newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
                    File f = new File(Smcfile.getName());
                    
                  ImageIO.write(newBufferedImage, "jpg", f);
                
                return f;

}
    
}
    

