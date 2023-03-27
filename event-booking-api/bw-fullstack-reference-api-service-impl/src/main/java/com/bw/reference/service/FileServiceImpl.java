package com.bw.reference.service;

import com.bw.entity.BwFile;
import com.bw.reference.dao.BwFileRepository;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
@RequiredArgsConstructor
@Named
public class FileServiceImpl implements FileService {
    private final BwFileRepository bwFileRepository;

    @Override
    public BwFile uploadFile(String originalFileName, String contentType, byte[] data) throws IOException {
        BwFile bwFile = new BwFile();
        bwFile.setContentType(contentType);
        bwFile.setDescription(originalFileName);
        bwFile.setData(data);
        bwFile.setDateCreated(Timestamp.from(Instant.now()));

        bwFileRepository.save(bwFile);
        return bwFile;
    }

    @Override
    public BwFile uploadFile(String originalFileName, String contentType, String data, String size) throws IOException {
        return null;
    }
}
