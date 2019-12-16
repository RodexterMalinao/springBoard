package com.activity.service;

import org.springframework.transaction.annotation.Transactional;

import com.activity.dto.DocumentDTO;

public interface DocumentSubmissionService {

	public abstract DocumentDTO createDocument(String pId, String pDocType,
			String pUser);

	@Transactional
	public abstract void submitDocument(String pId, DocumentDTO pDoc,
			String pUser);

	@Transactional
	public abstract void clearDocument(String pId, DocumentDTO pDoc,
			String pUser);

}