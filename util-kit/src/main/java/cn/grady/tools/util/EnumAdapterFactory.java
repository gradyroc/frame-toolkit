package cn.grady.tools.util;

import cn.grady.tools.common.enumeration.BasicEnum;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * @author grady
 * @version 1.0, on 1:10 2021/6/2.
 * 枚举通用处理，对Gson转换枚举进行有效性检验
 */
public class EnumAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Class<? super T> rawType = typeToken.getRawType();
        if (rawType.isEnum()) {
            return new BasicEnumTypeAdapter(rawType);
        }
        return null;
    }

    private static class BasicEnumTypeAdapter<T extends BasicEnum<String>> extends TypeAdapter<T> {
        final Class<T> rawType;

        public BasicEnumTypeAdapter(Class<T> rawType) {
            this.rawType = rawType;
        }

        @Override
        public void write(JsonWriter writer, T value) throws IOException {
            if (value==null){
                writer.nullValue();
                return;
            }
            writer.value(value.getValue());
        }

        @Override
        public T read(JsonReader reader) throws IOException {
            if (reader.peek()==JsonToken.NULL){
                reader.nextNull();
                return null;
            }

            String strValue=reader.nextString();
            if (StringUtils.isBlank(strValue)){
                return null;
            }

            T t = BasicEnumUtil.parse2Enum(rawType,strValue);
            if (t==null){

            }else {
                return t;
            }
            return null;
        }
    }
}
