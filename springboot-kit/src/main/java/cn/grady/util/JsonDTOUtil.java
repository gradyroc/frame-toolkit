package cn.grady.util;

import cn.grady.exception.PayloadException;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Json <==> Object 转换
 * @author
 */
public class JsonDTOUtil {
	public static final String DATE_FORMAT = DateUtil.TIME_FORMAT_LONG_WITH_MS; 

	/**
	 * 需要被排除的属性名转换字段，目的是兼容接口中的小写字段
	 */
	protected static final String[] NO_TRANSLATE_FIELDS = { 
			"output",
			"sys", 
			"response",
			"returnCode",
			"input", 
			"comm_req",
			"comm_res",
			"listfroz", 
			"listunfr", 
			"listacct", 
			"listfuzh",
			"listnm",
			"listclose",
			"chrgrtlst",
			"listnm_card",
			"listnm_cardhist", 
			"suspendBillDetail",
			"lstDxzYue"
	};

	private static Gson json2DTO;
	public static <T> T json2DTO(String jsonString, Class<T> clazz) throws PayloadException {
		if(json2DTO == null){
			synchronized(JsonDTOUtil.class){
				GsonBuilder builder = new GsonBuilder();
				builder.setDateFormat(DATE_FORMAT);
				builder.setFieldNamingStrategy(new DefaultFieldNamingPolicy());
				builder.registerTypeAdapter(BigDecimal.class, new BigDecimalTypeAdapter());
				builder.registerTypeAdapter(Long.class, new LongTypeAdapter());
				builder.registerTypeAdapter(Integer.class, new IntegerTypeAdapter());
                builder.registerTypeAdapterFactory(new EnumAdapterFactory());
                builder.addDeserializationExclusionStrategy(new IgnoreDeserializationStrategy());
				json2DTO = builder.disableHtmlEscaping().create();
			}
		}
		try{
			return json2DTO.fromJson(jsonString, clazz);			
		}catch(Exception e){
			throw new PayloadException(e);
		}
	}

	private static Gson json2DTONoNaming;
	public static <T> T json2DTONoNaming(String jsonString, Class<T> clazz) throws PayloadException{
		if(json2DTONoNaming == null){
			synchronized(JsonDTOUtil.class){
				GsonBuilder builder = new GsonBuilder();
				builder.setDateFormat(DATE_FORMAT);
				builder.registerTypeAdapter(BigDecimal.class, new BigDecimalTypeAdapter());
				builder.registerTypeAdapter(Long.class, new LongTypeAdapter());
				builder.registerTypeAdapter(Integer.class, new IntegerTypeAdapter());
                builder.registerTypeAdapterFactory(new EnumAdapterFactory());
                json2DTONoNaming = builder.disableHtmlEscaping().create();
			}
		}
		try{
			return json2DTONoNaming.fromJson(jsonString, clazz);			
		}catch(Exception e){
			throw new PayloadException(e);
		}
	}
	
	private static Gson map2DTO;
	public static <T> T map2DTO(Map<String, Object> map, Class<T> clazz) {
		if(map2DTO == null){
			synchronized(JsonDTOUtil.class){
				GsonBuilder builder = new GsonBuilder();
				builder.setDateFormat(DATE_FORMAT);
				builder.setFieldNamingStrategy(new DefaultFieldNamingPolicy());
				builder.registerTypeAdapter(BigDecimal.class, new BigDecimalTypeAdapter());
				builder.registerTypeAdapter(Long.class, new LongTypeAdapter());
				builder.registerTypeAdapter(Integer.class, new IntegerTypeAdapter());
                builder.registerTypeAdapterFactory(new EnumAdapterFactory());
				map2DTO= builder.disableHtmlEscaping().create();
			}
		}
		JsonElement element = map2DTO.toJsonTree(map);
		return map2DTO.fromJson(element, clazz);
	}

	private static Gson jsonElement2DTONoNaming;
	public static <T> T jsonElement2DTONoNaming(JsonElement element, Class<T> clazz) throws PayloadException{
		if(jsonElement2DTONoNaming == null){
			synchronized(JsonDTOUtil.class){
				GsonBuilder builder = new GsonBuilder();
				builder.setDateFormat(DATE_FORMAT);
				builder.registerTypeAdapter(BigDecimal.class, new BigDecimalTypeAdapter());
				builder.registerTypeAdapter(Long.class, new LongTypeAdapter());
				builder.registerTypeAdapter(Integer.class, new IntegerTypeAdapter());
                builder.registerTypeAdapterFactory(new EnumAdapterFactory());
                jsonElement2DTONoNaming = builder.disableHtmlEscaping().create();
			}
		}
		try{
			return jsonElement2DTONoNaming.fromJson(element, clazz);			
		}catch(Exception e){
			throw new PayloadException(e);
		}
	}

	private static Gson jsonElement2DTO;
	public static <T> T jsonElement2DTO(JsonElement element, Class<T> clazz) throws PayloadException{
		if(jsonElement2DTO == null){
			synchronized(JsonDTOUtil.class){
				GsonBuilder builder = new GsonBuilder();
				builder.setDateFormat(DATE_FORMAT);
				builder.setFieldNamingStrategy(new DefaultFieldNamingPolicy());
				builder.registerTypeAdapter(BigDecimal.class, new BigDecimalTypeAdapter());
				builder.registerTypeAdapter(Long.class, new LongTypeAdapter());
				builder.registerTypeAdapter(Integer.class, new IntegerTypeAdapter());
				// builder.registerTypeAdapter(String.class, new StringTypeAdapter());
                builder.registerTypeAdapterFactory(new EnumAdapterFactory());
				jsonElement2DTO = builder.disableHtmlEscaping().create();
			}
		}
		try{
			return jsonElement2DTO.fromJson(element, clazz);			
		}catch(Exception e){
			throw new PayloadException(e);
		}
	}
	
	private static Gson dto2Json;
	public static String dto2Json(Object obj) {
		if(dto2Json == null){
			synchronized(JsonDTOUtil.class){
				GsonBuilder builder = new GsonBuilder();
				builder.setDateFormat(DATE_FORMAT);
				builder.setFieldNamingStrategy(new DefaultFieldNamingPolicy());
				builder.excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT);
				builder.registerTypeAdapter(BigDecimal.class, new BigDecimalTypeAdapter());
				builder.registerTypeAdapter(Long.class, new LongTypeAdapter());
				builder.registerTypeAdapter(Integer.class, new IntegerTypeAdapter());
                builder.registerTypeAdapterFactory(new EnumAdapterFactory());
                builder.addSerializationExclusionStrategy(new IgnoreSerializationStrategy());
				builder.serializeNulls();
				dto2Json = builder.disableHtmlEscaping().create();
			}
		}
		return dto2Json.toJson(obj);
	}

	private static Gson dto2JsonNoNaming;
	public static String dto2JsonNoNaming(Object obj) {
		if(dto2JsonNoNaming == null){
			synchronized(JsonDTOUtil.class){
				GsonBuilder builder = new GsonBuilder();
				builder.setDateFormat(DATE_FORMAT);
				builder.excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT);
				builder.registerTypeAdapter(BigDecimal.class, new BigDecimalTypeAdapter());
				builder.registerTypeAdapter(Long.class, new LongTypeAdapter());
				builder.registerTypeAdapter(Integer.class, new IntegerTypeAdapter());
                builder.registerTypeAdapterFactory(new EnumAdapterFactory());
				builder.serializeNulls();
				dto2JsonNoNaming = builder.disableHtmlEscaping().create();
			}
		}
		return dto2JsonNoNaming.toJson(obj);
	}
	
	private static Gson dto2JsonNoNamingOmitNullValue;
	public static String dto2JsonNoNamingOmitNullValue(Object obj) {
		if(dto2JsonNoNamingOmitNullValue == null){
			synchronized(JsonDTOUtil.class){
				GsonBuilder builder = new GsonBuilder();
				builder.setDateFormat(DATE_FORMAT);
				builder.excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT);
				builder.registerTypeAdapter(BigDecimal.class, new BigDecimalTypeAdapter());
				builder.registerTypeAdapter(Long.class, new LongTypeAdapter());
				builder.registerTypeAdapter(Integer.class, new IntegerTypeAdapter());
                builder.registerTypeAdapterFactory(new EnumAdapterFactory());
				//builder.serializeNulls();
                dto2JsonNoNamingOmitNullValue = builder.disableHtmlEscaping().create();
			}
		}
		return dto2JsonNoNamingOmitNullValue.toJson(obj);
	}

	private static Gson dto2JsonElement;
	public static JsonElement dto2JsonElement(Object obj) {
		if(dto2JsonElement == null){
			synchronized(JsonDTOUtil.class){
				GsonBuilder builder = new GsonBuilder();
				builder.setDateFormat(DATE_FORMAT);
				builder.setFieldNamingStrategy(new DefaultFieldNamingPolicy());
				builder.excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT);
				builder.serializeNulls();
				dto2JsonElement = builder.disableHtmlEscaping().create();
			}
		}
		return dto2JsonElement.toJsonTree(obj);
	}

	private static Gson dto2JsonElementNoNaming;
	public static JsonElement dto2JsonElementNoNaming(Object obj) {
		if(dto2JsonElementNoNaming == null){
			synchronized(JsonDTOUtil.class){
				GsonBuilder builder = new GsonBuilder();
				builder.setDateFormat(DATE_FORMAT);
				builder.excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT);
				builder.serializeNulls();
				dto2JsonElementNoNaming = builder.disableHtmlEscaping().create();
			}
		}
		return dto2JsonElementNoNaming.toJsonTree(obj);
	}
	private static Gson dto2Map;
	@SuppressWarnings("unchecked")
	public static Map<String, Object> dto2Map(Object obj) {
		if(dto2Map == null){
			synchronized(JsonDTOUtil.class){
				GsonBuilder builder = new GsonBuilder();
				builder.setDateFormat(DATE_FORMAT);
				builder.excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT);
				builder.serializeNulls();
				dto2Map = builder.disableHtmlEscaping().create();
			}			
		}
		JsonElement element = dto2Map.toJsonTree(obj);
		Map<String, Object> map = new HashMap<String, Object>();
		return  dto2Map.fromJson(element, map.getClass());
	}

	private static Gson dto2JsonWithoutNullFields;
	public static String dto2JsonWithoutNullFields(Object obj) {
		if(dto2JsonWithoutNullFields == null){
			synchronized(JsonDTOUtil.class){
				GsonBuilder builder = new GsonBuilder();
				builder.setDateFormat(DATE_FORMAT);
				builder.setFieldNamingStrategy(new DefaultFieldNamingPolicy());
				builder.excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT);
				builder.registerTypeAdapter(BigDecimal.class, new BigDecimalTypeAdapter());
				builder.registerTypeAdapter(Long.class, new LongTypeAdapter());
				builder.registerTypeAdapter(Integer.class, new IntegerTypeAdapter());
                builder.registerTypeAdapterFactory(new EnumAdapterFactory());
				dto2JsonWithoutNullFields = builder.disableHtmlEscaping().create();
			}			
		}
		return dto2JsonWithoutNullFields.toJson(obj);
	}	
	
	/**
	 * Json field <==> Java field 转换规则，实例： CARD_NUMBER <==> cardNumber
	 * PAYEE_CARD_NO <==> payeeCardNo
	 */
	private static class DefaultFieldNamingPolicy implements FieldNamingStrategy {
		@Override
		public String translateName(Field f) {
			if (f == null || f.getName() == null) {
				return null;
			}

			String fname = f.getName();			
	        if (ArrayUtils.contains(NO_TRANSLATE_FIELDS, fname)) {
				return fname;
			}
			StringBuffer jname = new StringBuffer();
			int length = fname.length();
			for (int i = 0; i < length; i++) {
				char c = fname.charAt(i);
				if (Character.isLowerCase(fname.charAt(i)) || Character.isDigit(c)) {
					jname.append(Character.toUpperCase(c));
				} else {
					jname.append("_").append(Character.toUpperCase(c));
				}
			}

			return jname.toString();
		}
	}

	/**
	 * BigDecimal传入的空串转换成null 
	 */
	private static class BigDecimalTypeAdapter extends TypeAdapter<BigDecimal> {
		@Override
		public BigDecimal read(JsonReader reader) throws IOException {
			if (reader.peek() == JsonToken.NULL) {
				reader.nextNull();
				return null;
			}
			String stringValue = reader.nextString();
			if(stringValue.trim().equals("")){
				return null;
			}
			BigDecimal value = new BigDecimal(stringValue);
			return value;
		}

		@Override
		public void write(JsonWriter writer, BigDecimal value) throws IOException {
			if (value == null) {
				writer.nullValue();
				return;
			}
			// 2位小数
			value = value.setScale(2, BigDecimal.ROUND_HALF_DOWN);
			writer.value(value);
		}
	}

	/**
	 * Long传入的空串转换成null 
	 */
	private static class LongTypeAdapter extends TypeAdapter<Long>{
	    @Override
	    public Long read(JsonReader reader) throws IOException {
	        if(reader.peek() == JsonToken.NULL){
	            reader.nextNull();
	            return null;
	        }
	        String stringValue = reader.nextString();
	        if(stringValue.trim().equals("")){
	        	return null;
	        }
            Long value = Long.valueOf(stringValue);
            return value;
	    }
	    @Override
	    public void write(JsonWriter writer, Long value) throws IOException {
	        if (value == null) {
	            writer.nullValue();
	            return;
	        }
	        writer.value(value);
	    }
	}

	/**
	 * Integer传入的空串转换成null 
	 */
	private static class IntegerTypeAdapter extends TypeAdapter<Integer>{
	    @Override
	    public Integer read(JsonReader reader) throws IOException {
	        if(reader.peek() == JsonToken.NULL){
	            reader.nextNull();
	            return null;
	        }
	        String stringValue = reader.nextString();
	        if(stringValue.trim().equals("")){
	        	return null;
	        }
            Integer value = Integer.valueOf(stringValue);
            return value;
	    }
	    @Override
	    public void write(JsonWriter writer, Integer value) throws IOException {
	        if (value == null) {
	            writer.nullValue();
	            return;
	        }
	        writer.value(value);
	    }
	}
	
	@SuppressWarnings("unused")
	private static class StringTypeAdapter extends TypeAdapter<String>{
		@Override
	    public String read(JsonReader reader) throws IOException {
	        if(reader.peek() == JsonToken.NULL){
	            reader.nextNull();
	            return null;
	        }
	        String stringValue = reader.nextString();
	        return stringValue.trim();
	    }
	    @Override
	    public void write(JsonWriter writer, String value) throws IOException {
	        if (value == null) {
	            writer.nullValue();
	            return;
	        }
	        writer.value(value.trim());
	    }
	}
	
	private static class IgnoreDeserializationStrategy implements ExclusionStrategy{
		@Override
		public boolean shouldSkipField(FieldAttributes f) {
			if(f.getAnnotation(IgnoreDeserialization.class) != null) {
				return true;
			}
			return false;
		}

		@Override
		public boolean shouldSkipClass(Class<?> clazz) {
			return false;
		}
    }

	@Target({ElementType.FIELD})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface IgnoreDeserialization{
	}

	private static class IgnoreSerializationStrategy implements ExclusionStrategy{
		@Override
		public boolean shouldSkipField(FieldAttributes f) {
			if(f.getAnnotation(IgnoreSerialization.class) != null) {
				return true;
			}
			return false;
		}

		@Override
		public boolean shouldSkipClass(Class<?> clazz) {
			return false;
		}
	}

	@Target({ElementType.FIELD})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface IgnoreSerialization{
	}
	

	@SuppressWarnings("all")
//	private static class SampleDTO extends BasicDTO {
	private static class SampleDTO  {
		private String cardNumber = "222222";
		private String payeeCardNo = "333333";
		private BigDecimal amount = new BigDecimal("0.00000000");
		private Date txnDate = new Date();
		private String comm_req = null;
		private String descrpition = null;
		private Map<String, String> mapData = new HashMap<String, String>() {{
		    put("fieldAaa","");
		    put("fieldBbb","");
		    put("fieldXxxYyy","");
		}};
	}

	public static void main(String[] args) {
		System.out.println(json2DTO("{\"CARD_NUMBER\":\"222222\",\"PAYEE_CARD_NO\":\"333333\",\"AMOUNT\":0.00,\"TXN_DATE\":\"20170314 15:03:18.505\",\"comm_req\":\"null\",\"DESCRPITION\":null,\"mapData\":{\"fieldXxxYyy\":\"value999\",\"fieldAaa\":\"value111\",\"fieldBbb\":\"value222\"}}",SampleDTO.class));
//		System.out.println(dto2Json(new SampleDTO()));
//		System.out.println(dto2Json(new BigDecimal("0.00000000")));
	}
}
