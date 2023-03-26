package com.bw.reference.domain.account;

import com.bw.reference.util.ImageUtil;
import com.bw.reference.util.MediaUpload;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Base64;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */

@Getter
@Setter
public class PortalUserImageDto {
    @NotBlank
    private String profilePicture;

    private MediaUpload imageData;

    @Valid
    public MediaUpload getImageData() {

        if (imageData == null) {

            int dataStart = profilePicture.indexOf(",");
            byte[] bytes = Base64.getDecoder().decode(profilePicture.substring(Math.max(0, dataStart + 1)));

            imageData = new MediaUpload() {
                @Override
                public byte[] getData() {
                    return bytes;
                }

                @Override
                public String getContentType() {
                    return ImageUtil.getImageType(bytes);
                }
            };
        }

        return imageData;
    }
}