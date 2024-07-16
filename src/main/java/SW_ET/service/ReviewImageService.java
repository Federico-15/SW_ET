package SW_ET.service;/*
package SW_ET.service;

import SW_ET.dto.ReviewImageDto;
import SW_ET.entity.Review;
import SW_ET.entity.ReviewImages;
import SW_ET.repository.ReviewImageRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;



import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewImageService {

    @Autowired
    private ReviewImageRepository reviewImageRepository;

    private final Path rootLocation = Paths.get("review_images");

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    public List<ReviewImages> storeImages(List<MultipartFile> files, Long reviewId) throws IOException {
        List<ReviewImages> storedImages = new ArrayList<>();
        for (MultipartFile file : files) {
            ReviewImageDto imageDto = new ReviewImageDto();
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            Path destinationFile = rootLocation.resolve(filename)
                    .normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
                throw new RuntimeException("Cannot store file outside current directory.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                imageDto.setImageUrl(destinationFile.toUri().toString());
                imageDto.setImagePath(destinationFile.toString());
                imageDto.setFileName(filename);
                imageDto.setContentType(file.getContentType());
                imageDto.setFileSize(file.getSize());
                imageDto.setReviewId(reviewId);

                ReviewImages image = convertDtoToEntity(imageDto);
                storedImages.add(reviewImageRepository.save(image));
            }
        }
        return storedImages;
    }

    private ReviewImages convertDtoToEntity(ReviewImageDto dto) {
        ReviewImages image = new ReviewImages();
        image.setImageUrl(dto.getImageUrl());
        image.setImagePath(dto.getImagePath());
        image.setFileName(dto.getFileName());
        image.setContentType(dto.getContentType());
        image.setFileSize(dto.getFileSize());

        // Review 객체 생성 및 ID 설정
        Review review = new Review();
        review.setReviewId(dto.getReviewId()); // dto에서 받은 reviewId를 사용
        image.setReview(review); // ReviewImages의 review 객체 설정

        return image;
    }

}*/
