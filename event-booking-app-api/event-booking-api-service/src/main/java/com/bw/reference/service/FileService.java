package com.bw.reference.service;


import com.bw.entity.BwFile;

import java.io.IOException;

public interface FileService {
    BwFile uploadFile(String originalFileName, String contentType, byte[] data) throws IOException;
    BwFile uploadFile(String originalFileName, String contentType, String data, String size) throws IOException;

}
