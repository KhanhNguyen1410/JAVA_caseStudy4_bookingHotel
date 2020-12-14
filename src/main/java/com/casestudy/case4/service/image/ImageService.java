package com.casestudy.case4.service.image;

import com.casestudy.case4.model.Image;
import com.casestudy.case4.service.GeneralService;

import java.util.List;

public interface ImageService extends GeneralService<Image> {
     List<Image> findImagesByRoomId(Long id);
}
