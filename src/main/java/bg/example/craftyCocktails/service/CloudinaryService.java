package bg.example.craftyCocktails.service;

import com.cloudinary.Cloudinary;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CloudinaryService {

  private static final String TEMP_FILE = "temp-file";
  private static final String URL = "url";
  private static final String PUBLIC_ID = "public_id";

  private final Cloudinary cloudinary;

  public CloudinaryService(Cloudinary cloudinary) {
    this.cloudinary = cloudinary;
  }

  public CloudinaryImage uploadImage(MultipartFile multipartFile) throws IOException {
    File tempFile = File.createTempFile(TEMP_FILE, multipartFile.getOriginalFilename());
    multipartFile.transferTo(tempFile);

    try {
      @SuppressWarnings("unchecked")
      Map<String, String> uploadResult = this.cloudinary
          .uploader()
          .upload(tempFile, Map.of());

      String url = cloudinary.url().secure(true).generate(uploadResult.getOrDefault(PUBLIC_ID, ""));
      String publicId = uploadResult.getOrDefault(PUBLIC_ID, "");

      return new CloudinaryImage().setUrl(url).setPublicId(publicId);
    } finally {
      tempFile.delete();
    }
  }

  public boolean delete(String publicId) {
    try {
      this.cloudinary.uploader().destroy(publicId, Map.of());
    } catch (IOException e) {
      return false;
    }
    return true;
  }
}
