package com.crosskey.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.crosskey.dto.ProspectDTO;

public interface ProspectService {
	
	ProspectDTO processSingleProspect(ProspectDTO prospectDTO);
	
	public List<ProspectDTO> processProspect(MultipartFile file);
	
	public List<ProspectDTO> readAllProspect();
}
