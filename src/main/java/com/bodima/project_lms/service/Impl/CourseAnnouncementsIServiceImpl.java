package com.bodima.project_lms.service.Impl;

import com.bodima.project_lms.dto.CourseAnnouncements;
import com.bodima.project_lms.model.CourseAnnouncementsMoel;
import com.bodima.project_lms.repository.CourseAnnouncementsRepository;
import com.bodima.project_lms.service.CourseAnnouncementsService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CourseAnnouncementsIServiceImpl implements CourseAnnouncementsService {

    private final CourseAnnouncementsRepository courseAnnouncementsRepository;
    private final ModelMapper modelMapper;
    private final Cloudinary cloudinary;
    private static final String CLOUDINARY_ASSIGNMENTS_FOLDER = "course_materials/assignments";
    private static final String CLOUDINARY_NOTES_FOLDER = "course_materials/notes";
    private static final String CLOUDINARY_ANNOUNCEMENTS_FOLDER = "course_materials/announcements";
    private static final String CLOUDINARY_OTHER_UPLOADS_FOLDER = "course_materials/other_uploads";

    @Override
    public void addAnnouncements(CourseAnnouncements courseAnnouncements, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {

            String cloudinaryFolder;

            if (Boolean.TRUE.equals(courseAnnouncements.getIsAssignment())) {
                cloudinaryFolder = CLOUDINARY_ASSIGNMENTS_FOLDER;
            } else if (Boolean.TRUE.equals(courseAnnouncements.getIsNote())) {
                cloudinaryFolder = CLOUDINARY_NOTES_FOLDER;
            } else if (Boolean.TRUE.equals(courseAnnouncements.getIsAnnouncement())) {
                cloudinaryFolder = CLOUDINARY_ANNOUNCEMENTS_FOLDER;
            } else {
                // Default folder if none of the specific types are true
                cloudinaryFolder = CLOUDINARY_OTHER_UPLOADS_FOLDER;
            }

            Map<String, Object> uploadOptions = ObjectUtils.asMap(
                    "folder", cloudinaryFolder,
                    "resource_type", "auto" // Automatically detect resource type (image, video, raw)
            );



            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), uploadOptions);
            courseAnnouncements.setFileUrl(uploadResult.get("url").toString());
            courseAnnouncements.setOriginalFileName(file.getOriginalFilename());
            courseAnnouncements.setFileType(file.getContentType());
            courseAnnouncements.setFileSize(file.getSize());
            System.out.println(modelMapper.map(courseAnnouncements, CourseAnnouncementsMoel.class));
            courseAnnouncementsRepository.save(modelMapper.map(courseAnnouncements, CourseAnnouncementsMoel.class));
        }
    }

    @Override
    public List<CourseAnnouncements> getAnnouementsAndContents(int id) {
        return courseAnnouncementsRepository.findByCourseId(id);

    }
}
