package org.n52.project.enforce.fetching;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.n52.project.enforce.db.repository.DataRepository;
import org.n52.project.enforce.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
@ConfigurationProperties
public class MinkaApiFetcher {

	private URL outputDataUrl;

	private URL updateUrl;

	private ObjectMapper mapper;

	private String urlSpec;

	private Utils utils;

	private boolean initialize = false;

	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");

	private static Logger LOG = LoggerFactory.getLogger(MinkaApiFetcher.class);

	public MinkaApiFetcher(DataRepository dataRepository, Utils utils, Environment environment) {

		this.mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		this.utils = utils;
		urlSpec = environment.getProperty("minka.url.spec");
		initialize = Boolean.parseBoolean(environment.getProperty("minka.db.initialize"));
		try {
			updateUrl = new URI(String.format(environment.getProperty("minka.update.url.spec"),
					dateTimeFormatter.format(OffsetDateTime.now()))).toURL();
		} catch (URISyntaxException | MalformedURLException e) {
			LOG.error(e.getMessage());
		}

		try {
			outputDataUrl = findEndpoint();
		} catch (IOException e) {
			LOG.error(e.getMessage());
			throw new RuntimeException(e);
		}

		if (initialize) {
			//fetch 1000 observations
			if (LOG.isInfoEnabled()) {
				LOG.info("Initialize database.");
			}
			for (int i = 1; i < 21; i++) {
				String initialUrlSpecString = String.format(environment.getProperty("minka.initialize.url.spec"), i);
				if (LOG.isInfoEnabled()) {
					LOG.info(String.format("Fetching data from: %s.", initialUrlSpecString));
				}
				try {
					fetchAndStoreData(new URI(initialUrlSpecString).toURL());
					Thread.sleep(10000);
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}
			}
		}

		ScheduledExecutorService ses = Executors.newScheduledThreadPool(2);
		Runnable runnableTask = () -> {
			try {
				fetchAndStoreData();
			} catch (Exception e) {
				LOG.error(e.getMessage());
			}
		};

		long initialDelay = 0;
		long period = Long.valueOf(environment.getProperty("execution.period"));
		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("Starting ScheduledExecutorService with initialDelay: %d and period: %d.",
					initialDelay, period));
		}
		ses.scheduleAtFixedRate(runnableTask, initialDelay, period, TimeUnit.SECONDS);

		Runnable updateRunnableTask = () -> {
			try {
				checkForUpdates();
			} catch (Exception e) {
				LOG.error(e.getMessage());
			}
		};

		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("Starting update task with initialDelay: %d and period: 1 day.", initialDelay));
		}
		ses.scheduleAtFixedRate(updateRunnableTask, 1, 5, TimeUnit.HOURS);
	}

	private void checkForUpdates() throws Exception {
		JsonNode node = mapper.readTree(updateUrl);
		LOG.info("Fetched output.");
		if (node instanceof ObjectNode) {
			ObjectNode objectNode = (ObjectNode) node;
			JsonNode results = objectNode.path("results");
			if (results instanceof ArrayNode) {
				ArrayNode resultsArray = (ArrayNode) results;
				for (JsonNode jsonNode : resultsArray) {
					UUID id = utils.getId(jsonNode);
					if (utils.ckeckIdIsInDb(id)) {
						utils.updateData(jsonNode);
					}
				}
			}
		}

	}

	private void fetchAndStoreData() throws Exception {
		fetchAndStoreData(outputDataUrl);
	}
	
	private void fetchAndStoreData(URL url) throws Exception {
		JsonNode node = mapper.readTree(url);
		LOG.info("Fetched output.");
		if (node instanceof ObjectNode) {
			ObjectNode objectNode = (ObjectNode) node;
			JsonNode results = objectNode.path("results");
			if (results instanceof ArrayNode) {
				ArrayNode resultsArray = (ArrayNode) results;
				for (JsonNode jsonNode : resultsArray) {
					UUID id = utils.getId(jsonNode);
					if (utils.ckeckIdIsInDb(id)) {
						LOG.info(String.format("Data was with id %s already in database.", id.toString()));
						continue;
					}
					utils.createNewData(jsonNode);
					LOG.info("New Data was created.");
				}
			}
		}

	}

	public <T> T get(URL url, Class<T> type) throws StreamReadException, DatabindException, IOException {
		return mapper.readValue(url, type);
	}

	URL findEndpoint() throws MalformedURLException, IOException {
		URLConnection urlConnection = null;
		URL outputDataUrl = new URL(urlSpec);
		LOG.info("Checking endpoint: " + urlSpec);
		urlConnection = outputDataUrl.openConnection();
		urlConnection.setReadTimeout(3000);
		try {
			urlConnection.connect();
		} catch (Exception e) {
			LOG.info("Endpoint not available.", e);
		}
		if (urlConnection instanceof HttpURLConnection
				&& ((HttpURLConnection) urlConnection).getResponseCode() == 200) {
			LOG.info("Found endpoint at: " + urlSpec);
		}
		return outputDataUrl;
	}

}
