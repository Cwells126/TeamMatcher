package com.connectcard.controller;

import com.connectcard.domain.City;
import com.connectcard.domain.User;
import com.connectcard.service.LoginUser;
import com.connectcard.service.RetrieveCitiesAndStates;
import com.jigy.api.Helpful;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;


/**
 * This class is currently only used for it's static properties; however, it
 * could be used in the future if we decide to include asynchronous file upload
 * or some other enhancement to the calendar related to uploading attachments 
 * asynchronously
 * @author admin
 */
@Controller
public class IndexController extends BaseController {
    public static final String REDIRECT_URL = "redirectUrl";
    public static final String WEB_PROPERTIES = "web.properties";
    public static final String VALIDATED_PATH_LIST = "validatedPathList";
    public static final String MOBILE_CONTEXT = "mobile/";
    public static final String DESKTOP_CONTEXT = "desktop/";
    public static final String DEVICE_TYPE = "deviceType";
    public static final int MOBILE_DEVICE = 1;
    public static final int DESKTOP_DEVICE = 2;
    
    @Resource
    private LoginUser loginUser;
    
    @Resource
    private RetrieveCitiesAndStates retrieveCitiesAndStates;
    
    /**
     * stores an upload file to the file system
     * @param request the request object
     */
    @RequestMapping(value = "/index.htm")
    public String displayIndex(HttpServletRequest request, HttpServletResponse response) {
        // get the resolution of screen from the request
        int width = Helpful.getRequestParamIntSafe("width", request);
        int height = Helpful.getRequestParamIntSafe("height", request);
        
        if(width > 1000 || height > 1000){
            Helpful.setObjInSession(request, DEVICE_TYPE, DESKTOP_DEVICE);
        } else {
            Helpful.setObjInSession(request, DEVICE_TYPE, MOBILE_DEVICE);
        }
        
        // put the list of paths to validate in session
        if(request.isRequestedSessionIdValid() && request.getSession().getAttribute(VALIDATED_PATH_LIST) == null){
            Helpful.setObjInSession(request, VALIDATED_PATH_LIST, getValidatedPaths());
        }
       
       List<City> cities =  retrieveCitiesAndStates.retrieveAllCities();
       
       
       
               if(cities != null){
            Helpful.setObjInSession(request, "cities", cities);
         //   super.sendJsonToPage(response, request, cities);
        } else {
            super.sendMessageToPageAsJson(response, request, "error", "No Cities Displayed. Please Try Again.");
        }
        
        return "index";
    }
    
    
    
    /**
     * This method logs the user in
     * @param request the request object
     * @param response the response object
     */
    @RequestMapping(value = "/login.htm")
    public void login(HttpServletRequest request, HttpServletResponse response) {
        User user = loginUser.loginUser("", "");
        if(user != null){
            Helpful.setObjInSession(request, "user", user);
            super.sendJsonToPage(response, request, user);
        } else {
            super.sendMessageToPageAsJson(response, request, "error", "Username/Password Incorrect. Please Try Again.");
        }
    }
        
    
    
    
    /**
     * This method creates a list of paths that need to be validated
     * prior to allowing the user to access them. The spring interceptor
     * will check to make sure the user has logged in before allowing the
     * user access to the pages in this list
     * @return the list of application paths to validate
     */
    public static List<String> getValidatedPaths(){
        List<String> validatedPathList = new ArrayList<String>();
        validatedPathList.add("/connectCard.htm");
      
        return validatedPathList;
    }
}