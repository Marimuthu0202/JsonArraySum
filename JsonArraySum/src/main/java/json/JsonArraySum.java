package json;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonArraySum {

	private static String readJsonFromService(String url) {
		String responseString = null;
		try {
			URL urlObject = new URL(url);
			responseString = new BufferedReader(new InputStreamReader(urlObject.openStream())).lines()
					.collect(Collectors.joining("\n"));
			System.out.println(responseString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseString;
	}

	private static String readUrl(String[] args) {
		String url = (args != null && args.length > 0 && args[0] != null) ? args[0] : null;
		if (url == null) {
			try {
				InputStream input = new FileInputStream(
						"E:\\eclipse_workspace\\\\JsonArraySum\\src\\main\\resources\\application.properties");
				Properties prop = new Properties();
				prop.load(input);
				System.out.println("From PropertyFile url- " + prop.getProperty("url"));
				url = prop.getProperty("url");
				if (input != null) {
					input.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			if (url == null)
				url = "http://www.mocky.io/v2/5bf06f282f0000c8147a0c89";
		}
		return url;
	}

	public static void main(String[] args) {
		try {
			String responseString = readJsonFromService(readUrl(args));
			JSONArray jsonArray = new JSONArray(responseString);
			AtomicInteger programTotal = new AtomicInteger();
			jsonArray.forEach(item -> {
			    JSONObject obj = (JSONObject) item;
			    JSONArray jsonArrayLocal = obj.getJSONArray("numbers");
			    int subTotal = 0;
				for (Object value : jsonArrayLocal) {
					subTotal += Integer.parseInt(value.toString());
				}
				programTotal.set(programTotal.get()+subTotal);
				System.out.println("subTotal for numbers:" + subTotal);
			});
			System.out.println("programTotal for numbers:" + programTotal.get());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
