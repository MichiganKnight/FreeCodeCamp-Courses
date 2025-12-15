package com.revature.weekNine.annotationBased;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Controller
public class GalleryController {
    private GalleryService galleryService;

    public GalleryController(GalleryService galleryService) {
        this.galleryService = galleryService;
    }
}

@Service
class GalleryService {
    private GalleryRepository galleryRepository;

    public void setGalleryRepository(GalleryRepository galleryRepository) {
        this.galleryRepository = galleryRepository;
    }
}

@Repository
class GalleryRepository {}

