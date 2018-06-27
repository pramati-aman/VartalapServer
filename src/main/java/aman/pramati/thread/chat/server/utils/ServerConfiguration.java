package aman.pramati.thread.chat.server.utils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:server.properties")
@ConfigurationProperties(prefix="server")
public class ServerConfiguration {
	
	private String authmethod;
	
	
	private String user;
	
	@Length(max=20,min=8)
	@Pattern(regexp="[A-Za-z0-9_%@]*")
	private String password;
	
	
	private String host;
	
	@Min(1025)
	@Max(65536)
	private Integer port;
	
	@Min(20)
	@Max(200)
	private Integer maxQueueLength;
	
	public String getAuthmethod() {
		return authmethod;
	}
	public void setAuthmethod(String authmethod) {
		this.authmethod = authmethod;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(@Min(1025) @Max(65536) Integer port) {
		this.port = port;
	}
	public Integer getMaxQueueLength() {
		return maxQueueLength;
	}
	public void setMaxQueueLength(Integer maxQueueLength) {
		this.maxQueueLength = maxQueueLength;
	}
	
}
