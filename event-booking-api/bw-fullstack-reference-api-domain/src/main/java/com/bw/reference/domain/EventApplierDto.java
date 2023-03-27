package com.bw.reference.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class EventApplierDto {
    @NotBlank
    @Size(max = 25)
    private String firstName;

    @NotBlank
    @Size(max = 25)
    private String middleName;

    @NotBlank
    @Size(max = 25)
    private String lastName;

    @NotBlank
    @Size(max = 11)
    private String phoneNumber;

    @Size(max = 50)
    @NotBlank
    private String emailAddress;

    @Size(max = 10)
    private Long eventId;

    private List<UploadedFile> uploadedFilesList;

    @Getter
    @Setter
    public static class UploadedFile {
        private String fileName;
        private String fileType;
        private String fileSize;
        private String fileData64;
    }


}
