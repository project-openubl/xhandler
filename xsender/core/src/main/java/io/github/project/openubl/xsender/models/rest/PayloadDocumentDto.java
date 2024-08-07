package io.github.project.openubl.xsender.models.rest;

import io.github.project.openubl.xsender.files.ZipFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayloadDocumentDto {

    private Archivo archivo;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Archivo {
        private String nomArchivo;
        private String arcGreZip;
        private String hashZip;
    }

    public static PayloadDocumentDto build(ZipFile zipFile) {
        // Get Hash
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hashZip = digest.digest(zipFile.getFile());

        // Get Base64
        byte[] base64Zip = Base64.getEncoder().encode(zipFile.getFile());

        // Result
        return PayloadDocumentDto.builder()
                .archivo(PayloadDocumentDto.Archivo.builder()
                        .nomArchivo(zipFile.getFilename())
                        .arcGreZip(new String(base64Zip))
                        .hashZip(new String(Hex.encodeHex(hashZip)))
                        .build()
                )
                .build();
    }
}
