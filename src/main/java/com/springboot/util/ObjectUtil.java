package com.springboot.util;

import java.io.*;

/**
 * Created by ibong-gi on 2016. 8. 10..
 */
public class ObjectUtil {
    public static byte[] toByte(Object o) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(o);
        byte[] binaryObject = bos.toByteArray();
        out.close();
        bos.close();
        return binaryObject;
    }

    public static Object toObject(byte[] bo) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bo);
        ObjectInput in = new ObjectInputStream(bis);
        Object o = in.readObject();
        in.close();
        bis.close();
        return o;
    }
}
