package com.weather;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.commons.io.IOUtils;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

/*
 * from: https://www.infoq.com/articles/Wiremock-testing-mocking-over-wire-stubs
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceApplicationTests {

	private final String serverUrl = "http://localhost:8080";

	private final CloseableHttpClient httpclient = HttpClients.createDefault();

	private ConfigurableApplicationContext appContext;

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(8089); // No-args
																// constructor
																// defaults to
																// port 8080

	@Before
	public void setUp() {
		appContext = SpringApplication.run(ServiceApplication.class, new String[0]);
	}

	@After
	public void tearDown() {
		SpringApplication.exit(appContext, new ExitCodeGenerator() {
			@Override
			public int getExitCode() {
				return 0;
			}
		});
	}

	@Test
	public void testGetCurrentWeatherSuccess() throws ClientProtocolException, IOException {

		String reqUrl = "/api/v1/current?city=Beijing";
		stubFor(get(urlEqualTo(reqUrl))
				.willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")));

		HttpGet httpget = new HttpGet(serverUrl + reqUrl);
		httpclient.execute(httpget);
		CloseableHttpResponse response = httpclient.execute(httpget);

		assertEquals(200, response.getStatusLine().getStatusCode());
		// @todo: check the content is a valid response or not

		response.close();
	}

	@Test
	public void testGetCurrentWeatherFailure() throws ClientProtocolException, IOException {

		String reqUrl = "/api/v1/current?city=NotFound";
		stubFor(get(urlEqualTo(reqUrl))
				.willReturn(aResponse().withStatus(404).withHeader("Content-Type", "application/json")));

		HttpGet httpget = new HttpGet(serverUrl + reqUrl);
		httpclient.execute(httpget);
		CloseableHttpResponse response = httpclient.execute(httpget);

		assertEquals(404, response.getStatusLine().getStatusCode());
		assertEquals(
				"{\"path\":\"/api/v1/current\",\"reason\":\"city NotFound's current weather data does not exist\"}",
				IOUtils.toString(response.getEntity().getContent(), Charset.defaultCharset()));

		response.close();
	}

	@Test
	public void testRouterFailure() throws ClientProtocolException, IOException {
		String reqUrl = "/api/v1/current/notfound";
		stubFor(get(urlEqualTo(reqUrl))
				.willReturn(aResponse().withStatus(404).withHeader("Content-Type", "application/json")));

		HttpGet httpget = new HttpGet(serverUrl + reqUrl);
		httpclient.execute(httpget);
		CloseableHttpResponse response = httpclient.execute(httpget);

		assertEquals(404, response.getStatusLine().getStatusCode());
		assertEquals("{\"reason\":\"Not Found\"}",
				IOUtils.toString(response.getEntity().getContent(), Charset.defaultCharset()));

		response.close();
	}
}
