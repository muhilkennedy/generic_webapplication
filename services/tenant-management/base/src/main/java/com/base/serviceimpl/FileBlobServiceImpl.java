package com.base.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.dao.FileBlobRepository;
import com.base.entity.BaseObject;
import com.base.entity.FileBlob;
import com.base.service.FileBlobService;

/**
 * @author Muhil
 *
 */
@Service
public class FileBlobServiceImpl implements FileBlobService {
	
	@Autowired
	private FileBlobRepository blobRepository;

	@Override
	public Object save(BaseObject obj) {
		return blobRepository.save((FileBlob) obj);
	}

	@Override
	public Object saveAndFlush(BaseObject obj) {
		return blobRepository.saveAndFlush((FileBlob) obj);
	}

	@Override
	public FileBlob findById(Object rootId) {
		return blobRepository.findById((Long) rootId).get();
	}

	@Override
	public void delete(BaseObject obj) {
		blobRepository.delete((FileBlob)obj);
	}

	@Override
	public String findURLByImageId(long id) {
		FileBlob blob = findById(id);
		return (blob != null) ? blob.getUrl() : null;
	}
	
	
	

}
