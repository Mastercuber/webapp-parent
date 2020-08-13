package de.avensio.common.persistence.model.shop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.avensio.common.persistence.IWithId;
import de.avensio.common.persistence.IWithName;
import de.avensio.common.persistence.model.INameableEntity;
import lombok.extern.java.Log;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Base64;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Entity
@Log
public class Image implements INameableEntity, IWithName, IWithId, Serializable {

    @Id
    @Column(name = "IMAGE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String mimeType;

    @Column(nullable = false, columnDefinition = "mediumblob")
    @JsonIgnore
    private byte[] data;

    public Image() {
        super();
    }

    public Image(final String name, final String mimeType, final byte[] data) {
        super();
        this.name = name;
        this.mimeType = mimeType;
        this.data = compressBytes(data);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public byte[] getData() {
        return decompressBytes(this.data);
    }

    public String getDataUri() {
        return "data:" + getMimeType() + ";base64," + Base64.getEncoder().encodeToString(getData());
    }

    public void setData(byte[] data) {
        this.data = compressBytes(data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;

        return new EqualsBuilder()
                .append(name, image.name)
                .append(mimeType, image.mimeType)
                .append(data, image.data)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(7627, 125)
                .append(id)
                .append(name)
                .append(mimeType)
                .append(data)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("mimeType", mimeType)
                .append("data", data)
                .toString();
    }


    private byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];

        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }

        try {
            outputStream.close();
        } catch (IOException e) {
            log.info("Data can't be compressed!");
            e.printStackTrace();
        }

        System.out.println("Compressed Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    private byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException e) {
            log.info("Data can't be decompressed!");
            e.printStackTrace();
        }
        System.out.println("Decompressed Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }
}
