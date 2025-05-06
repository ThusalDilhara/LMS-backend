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
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CourseAnnouncementsIServiceImpl implements CourseAnnouncementsService {

    private final CourseAnnouncementsRepository courseAnnouncementsRepository;
    private final ModelMapper modelMapper;
    private final Cloudinary cloudinary;


    @Override
    public void addAnnouncements(CourseAnnouncements courseAnnouncements, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            courseAnnouncements.setFileUrl(uploadResult.get("url").toString());
            courseAnnouncements.setOriginalFileName(file.getOriginalFilename());
            courseAnnouncements.setFileType(file.getContentType());
            courseAnnouncements.setFileSize(file.getSize());

            courseAnnouncementsRepository.save(modelMapper.map(courseAnnouncements, CourseAnnouncementsMoel.class));
        }
    }
}
