import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.fluent.Request;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ISSLocator {

	public static Location getISSLocation(){
	    String response = "";

	    try{
	        response = Request.Get("http://api.open-notify.org/iss-now.json").execute().returnContent().asString();
	    }catch(IOException ex){
	        ex.printStackTrace();
	    }

	    JsonValue value = Json.parse(response);
	    JsonObject jsonObject = value.asObject();
	    JsonObject location = jsonObject.get("iss_position").asObject();
	    String latitude = location.get("latitude").asString();
	    String longitude = location.get("longitude").asString();
	    return new Location(latitude, longitude);
	}

	
	public static void openMap(Location location){
//		https://www.google.com/maps/preview/@-15.623037,18.388672,8z
		String url = "https://www.google.com/maps/preview/@" + location.getLatitude() + "," + location.getLongitude()+",8z";
		try {
			Desktop.getDesktop().browse(new URI(url));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
	}
	
//	"http://maps.google.com/maps?t=m&q=loc:" + str(lat) + "+" + str(long)
		
	public static void main(String[] args){
		Location issLocation = getISSLocation();
		openMap(issLocation);
	}
	
}
