import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import models.Category;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;
import play.api.mvc.RequestHeader;
import play.api.mvc.Result;
import play.db.jpa.JPA;
import play.libs.F.Function0;
import data.JSONDataInserter;

public class Global extends GlobalSettings {
	
	@play.db.jpa.Transactional
	public static void insertJSonData() throws IOException {
		String file = Play.application().configuration().getString("questions.filePath");		
		Logger.info("Data from: " + file);
		InputStream is = Play.application().resourceAsStream(file);
		List<Category> categories = JSONDataInserter.insertData(is);
		Logger.info(categories.size() + " categories from json file '" + file + "' inserted.");
	}
	
	@play.db.jpa.Transactional
    public void onStart(Application app) {
       try {
    	   JPA.withTransaction(new Function0<Boolean>() {

			@Override
			public Boolean apply() throws Throwable {
				insertJSonData();
				return true;
			}
			   
			});
       } catch (Throwable e) {
    	   e.printStackTrace();
       }
    }

    public void onStop(Application app) {
        Logger.info("Application shutdown...");
    }
    
    // called when a route is found, but it was not possible to bind the request parameters
    public Result onBadRequest(RequestHeader request, String error) {
        Logger.error("Bad Request: " + error);
        return null;
      } 
     
      // 500 - internal server error
    public Result onError(RequestHeader request, Throwable throwable) {
        Logger.error("Internal server error: "+ throwable.getMessage());
        return null;
      }
     
      // 404 - page not found error
    public Result onHandlerNotFound(RequestHeader request) {
        Logger.error("HandlerNotFound: "+request.toString());
        return null;
      }

}