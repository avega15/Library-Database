package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JsonParseService implements ParsingService{

	@Autowired
	private RestTemplate restTemplate;
	
	public Object parse(String url) {
		return restTemplate.getForObject(url, Object.class);
	}

}
