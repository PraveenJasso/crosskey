package com.crosskey.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.crosskey.dto.ProspectDTO;
import com.crosskey.service.ProspectService;


@RestController
public class ProspectsController {

    @Autowired
    private ProspectService prospectService;
    
    @CrossOrigin("*")
    @PostMapping(value = "/prospect")
	public ResponseEntity<HttpStatus> save(@RequestBody ProspectDTO prospect) throws Exception {
    	 prospectService.processSingleProspect(prospect);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
    
    @CrossOrigin("*")
    @PostMapping("/upload")
    public List<ProspectDTO> singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        return prospectService.processProspect(file);

    }
    
    @GetMapping(value = "/prospect")
	public ResponseEntity<List<ProspectDTO>> getAllProspects() throws Exception {
    	List<ProspectDTO> prospects = this.prospectService.readAllProspect();
		return new ResponseEntity<List<ProspectDTO>>(prospects, HttpStatus.OK);
	}
}
