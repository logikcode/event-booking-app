package com.bw.reference.util;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public interface MediaUpload {
    @NotNull
    @Size(max = 1024 * 800)
    byte[] getData();

    @NotBlank
    @Pattern(regexp = "image/.*", flags = Pattern.Flag.CASE_INSENSITIVE)
    String getContentType();
}
