import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class XlassLoader extends ClassLoader{
    public static void main(String[] args) {
        try {
            //反射调用hello方法，是否有别的方法调用
            Object h = new XlassLoader().findClass("Hello").newInstance();
            h.getClass().getDeclaredMethod("hello").invoke(h);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        String fileName ="./Hello.xlass";

        File file = new File(fileName);

        long fileSize = file.length();
        FileInputStream fi = null;
        try {
            fi = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (true) {
            try {
                if (!(offset < buffer.length
                                && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            offset += numRead;
        }

        try {
            fi.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0;i<buffer.length;i++) {
            buffer[i] = (byte) (255 - buffer[i]);
        }

        return defineClass(name, buffer, 0, buffer.length);
    }
}
