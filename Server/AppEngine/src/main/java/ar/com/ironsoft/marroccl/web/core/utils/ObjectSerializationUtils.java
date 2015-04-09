package ar.com.ironsoft.marroccl.web.core.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;

/**
 * @author Tomas de Priede
 */
public class ObjectSerializationUtils {

    public static Serializable deserialize(byte[] bytesIn) throws IOException,
            ClassNotFoundException {
        Serializable result = null;
        ObjectInputStream objectIn = null;
        try {
            bytesIn = Base64.decodeBase64(bytesIn);
            objectIn = new ObjectInputStream(new BufferedInputStream(
                    new ByteArrayInputStream(bytesIn)));
            result = (Serializable) objectIn.readObject();
        } finally {
            try {
                if (objectIn != null)
                    objectIn.close();
            } catch (IOException localIOException) {
            }
        }
        return result;
    }

    public static byte[] serialize(Object obj) throws IOException {
        byte[] result = (byte[]) null;
        ObjectOutputStream objectOut = null;
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        try {
            objectOut = new ObjectOutputStream(new BufferedOutputStream(
                    bytesOut));
            objectOut.writeObject(obj);
            objectOut.flush();
        } finally {
            if (objectOut != null)
                try {
                    objectOut.close();
                    result = Base64.encodeBase64(bytesOut.toByteArray());
                } catch (IOException localIOException) {
                }
        }
        return result;
    }

    public static final byte[] getBytes(HttpServletRequest req, Logger logger) {
        byte[] data = (byte[]) null;
        int length = req.getContentLength();
        if (length == 0)
            logger.severe("request content length is 0");
        else {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[8192];
                ServletInputStream sis = req.getInputStream();
                int len;
                while ((len = sis.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                data = baos.toByteArray();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error deserializing task", e);
            }
        }
        return data;
    }
}
