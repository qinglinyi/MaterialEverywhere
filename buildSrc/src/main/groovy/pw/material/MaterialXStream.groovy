package pw.material

import com.thoughtworks.xstream.XStream

import java.nio.charset.Charset

public class MaterialXStream {
    private static final Charset CHARSET = Charset.forName('utf-8')

    public static XStream newInstance() {
        XStream xStream = new XStream();
        xStream.autodetectAnnotations true
        return xStream
    }

    public static byte[] toXML(Object o) {
        return newInstance().toXML(o).getBytes(CHARSET)
    }
}
