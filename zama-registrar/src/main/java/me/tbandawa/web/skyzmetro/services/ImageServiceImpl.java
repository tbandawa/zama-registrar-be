package me.tbandawa.web.skyzmetro.services;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import net.coobird.thumbnailator.Thumbnails;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import me.tbandawa.web.skyzmetro.dtos.PhotoDto;
import me.tbandawa.web.skyzmetro.exceptions.FileStorageException;
import me.tbandawa.web.skyzmetro.exceptions.InvalidFileTypeException;
import me.tbandawa.web.skyzmetro.utils.ResourceType;

import javax.imageio.ImageIO;

/**
 * Defines image file operations.
 */
@Service
public class ImageServiceImpl implements ImageService {
	
	private static final List<String> imageTypes = Arrays.asList("image/png", "image/jpeg", "image/jpeg");
	
	@Value("${members.images}")
	private String membersImageFolder;

	/**
	 * Iterate through images, check if image is valid
	 * and save to a directory.
	 * @param id directory name
	 * @return images list of images
	 */
	@Override
	public PhotoDto saveImage(Long id, MultipartFile image, ResourceType resourceType) {

		// Check if it's a valid image file
		if(!imageTypes.contains(image.getContentType()))
			throw new InvalidFileTypeException(image.getOriginalFilename() + " is not a valid image.");


		// Save and return its URI relative to the server
		return saveImage(id, image, resourceFolder(resourceType));
	}

	/**
	 * Get folder contents as a list of URIs.
	 * @param id directory name
	 * @return list of URIs
	 */
	@Override
	public PhotoDto getImage(Long id, ResourceType resourceType) {
		List<String> imageURIs;
		PhotoDto photoDto = new PhotoDto();
		try {
			
			// Iterate folder and return its content as a list of URIs
			imageURIs = Files.list(Paths.get(resourceFolder(resourceType) + File.separatorChar + id + File.separatorChar))
		            .map(Path::toFile)
		            .map(File::getPath)
		            .map(filePath ->
		            	ServletUriComponentsBuilder.fromCurrentContextPath()
		            		.path(filePath)
		            		.toUriString()
		            )
		            .collect(Collectors.toList());

			String imageURI = imageURIs.stream()
					.filter(s  -> s.contains("photo"))
					.findFirst()
					.orElse(null);

			String thumbnailURI = imageURIs.stream()
					.filter(s  -> s.contains("thumbnail"))
					.findFirst()
					.orElse(null);

			photoDto.setPhoto(imageURI);
			photoDto.setThumbnail(thumbnailURI);
			
		} catch (IOException ex) {
			System.out.println(ex);
		}
		return photoDto;
	}

	/**
	 * Delete recursively directory.
	 * @param id folder name
	 */
	@Override
	public void deleteImage(Long id, ResourceType resourceType) {
		// Delete image folder using Spring's file utilities
		FileSystemUtils.deleteRecursively(new File(resourceFolder(resourceType) + File.separatorChar + id));
	}
	
	/**
	 * Save image in the folder created and named using identifier. The
	 * image name is created by appending <b>imageIndex</b> to <b>image_</b>.
	 *
	 * @param id    of the created gallery
	 * @param image file to save
	 * @return Images of the saved image
	 */
	private static PhotoDto saveImage(Long id, MultipartFile image, String folderName) {

		PhotoDto images = new PhotoDto();

		// Build image path using identifier
		Path imageUploadPath = Paths
				.get(folderName + File.separatorChar + id + File.separatorChar)
				.toAbsolutePath()
				.normalize();

		// Create image destination folder
		try {
            Files.createDirectories(imageUploadPath);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory.");
        }

		// Get image extension
		int arrSize = Objects.requireNonNull(image.getOriginalFilename()).split("\\.").length;
		String imageExtension = Objects.requireNonNull(image.getOriginalFilename()).split("\\.")[arrSize - 1];

		// Name the original image file by appending imageIndex to "image_"
		String originalImageName = createFileName("photo", imageExtension);

		// Name the thumbnail image file by appending imageIndex to "thumbnail_"
		String thumbnailImageName = createFileName("thumbnail", imageExtension);

		// Create, save and return thumbnail URI
		images.setThumbnail(saveThumbnail(imageUploadPath, thumbnailImageName, imageExtension, id, image, folderName));

		// Save image and return URI
		images.setPhoto(copyImageToFileSystem(imageUploadPath, originalImageName, id, image, folderName));
		
		return images;
	}

	private static String copyImageToFileSystem(Path path, String imageName, Long id, MultipartFile image, String folderName) {
		String imageUri = null;
		try {

			// Copy image file storage
			Path targetLocation = path.resolve(imageName);
			Files.copy(image.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			// Generate and return image URI
			imageUri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path(folderName + File.separatorChar + id + File.separatorChar)
					.path(imageName)
					.toUriString();

		} catch (IOException ex) {
			ex.printStackTrace();
			throw new FileStorageException("Could not store image " + imageName);
		}
		return imageUri;
	}

	private static String saveThumbnail(Path path, String imageName, String imageExtension, Long id, MultipartFile image, String folderName) {
		String thumbnailUri = null;
		File imageFile = null;
		try {
			imageFile = convert(image);
			BufferedImage inputImage = ImageIO.read(imageFile);
			BufferedImage outputImage = resizeImage(inputImage, imageExtension);
			Path targetLocation = path.resolve(imageName);
			if (ImageIO.write(outputImage, imageExtension, targetLocation.toAbsolutePath().toFile())) {
				// Generate and return thumbnail URI
				thumbnailUri = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path(folderName + File.separatorChar + id + File.separatorChar)
						.path(imageName)
						.toUriString();
			};
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new FileStorageException("Could not save thumbnail " + imageName);
		} finally {
			// Delete imageFile created
			assert imageFile != null;
			if(imageFile.exists()){
				imageFile.delete();
			}

		}
		return thumbnailUri;
	}

	private static BufferedImage resizeImage(BufferedImage originalImage,String imageExtension) throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Thumbnails.of(originalImage)
				.size(300, 300)
				.outputFormat(imageExtension)
				.outputQuality(0.8f)
				.toOutputStream(outputStream);
		byte[] data = outputStream.toByteArray();
		ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
		return ImageIO.read(inputStream);
	}

	private static File convert(MultipartFile multipartFile) {
		File convFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
		try {
			convFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convFile);
			fos.write(multipartFile.getBytes());
			fos.close();
		} catch (IOException e) {
			convFile = null;
		}
		return convFile;
	}
	
	private static String createFileName(String fileName, String imageExtension) {
		return fileName + "." + imageExtension;
	}
	
	private String resourceFolder(ResourceType resourceType) {
		String resorceFolderName = "";
        if (Objects.requireNonNull(resourceType) == ResourceType.ZAMA_MEMBER) {
            resorceFolderName = membersImageFolder;
        }
		return resorceFolderName;
	}
}