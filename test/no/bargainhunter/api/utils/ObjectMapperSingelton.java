package no.bargainhunter.api.utils;

import org.codehaus.jackson.map.ObjectMapper;

public class ObjectMapperSingelton {

private static ObjectMapper mObjectMapper;
	
	public synchronized static ObjectMapper getObjectMapper() {
		if (mObjectMapper == null) {
			mObjectMapper = new ObjectMapper();
		}
		return mObjectMapper;
	}
}
