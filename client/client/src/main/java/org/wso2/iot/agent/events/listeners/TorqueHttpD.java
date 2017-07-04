package org.wso2.iot.agent.events.listeners;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.wso2.iot.agent.events.listeners.OBDTemplates.OBDIIReaderTestTemplate;

import java.io.IOException;

/**
 * Created by hhiroot on 4/20/17.
 */

public class TorqueHttpD extends NanoHTTPD {
    private final static int PORT = 8080;
    private String clientMessage;
    public TorqueHttpD() throws IOException {
        super(PORT);
        //start();
        System.out.println( "\nRunning! Point your browers to http://localhost:8080/ \n" );
    }

    @Override
    public Response serve(IHTTPSession session) {
        clientMessages = session.getMessages();
        String msg = "<html><body><h1>Hello server</h1>\n";
        //msg += "<p>We serve " + session.getUri() + " !</p>";
        msg += "<p>We serve " + session.getMessages() + " !</p>";
        return newFixedLengthResponse(msg + "</body></html>\n");
    }
    public String getMessage (){
        String resultMessage = convert4DAS();
        return clientMessage;
    }
    private String convert4DAS(){
        String result;
        String examMessage = "{\"altitude\":0.0,\"readings\":{\"ENGINE_RPM\":\"0RPM\",\"BAROMETRIC_PRESSURE\":\"0kPa\",\"WIDEBAND_AIR_FUEL_RATIO\":\"0.00:1 AFR\",\"TROUBLE_CODES\":\"\",\"THROTTLE_POS\":\"0.0%\",\"Long Term Fuel Trim Bank 1\":\"-100.0%\",\"Long Term Fuel Trim Bank 2\":\"-100.0%\",\"SPEED\":\"0km\\/h\",\"FUEL_PRESSURE\":\"0kPa\",\"DTC_NUMBER\":\"MIL is OFF0 codes\",\"FUEL_RAIL_PRESSURE\":\"0kPa\",\"ENGINE_OIL_TEMP\":\"-40C\",\"AIR_INTAKE_TEMP\":\"-40C\",\"AIR_FUEL_RATIO\":\"0.00:1 AFR\",\"ENGINE_COOLANT_TEMP\":\"-40C\",\"FUEL_LEVEL\":\"0.0%\",\"Short Term Fuel Trim Bank 1\":\"-100.0%\",\"TIMING_ADVANCE\":\"0.0%\",\"Short Term Fuel Trim Bank 2\":\"-100.0%\",\"EQUIV_RATIO\":\"0.0%\",\"DISTANCE_TRAVELED_MIL_ON\":\"0km\",\"MAF\":\"0.00g\\/s\",\"AMBIENT_AIR_TEMP\":\"-40C\",\"ENGINE_RUNTIME\":\"00:00:00\",\"CONTROL_MODULE_VOLTAGE\":\"0.0V\",\"FUEL_TYPE\":\"-\",\"VIN\":\"A\",\"ENGINE_LOAD\":\"0.0%\",\"FUEL_CONSUMPTION_RATE\":\"0.0L\\/h\",\"INTAKE_MANIFOLD_PRESSURE\":\"0kPa\"},\"latitude\":0.0,\"vehicleid\":\"\",\"longitude\":0.0,\"timestamp\":1497955213951}";
        Gson gson = new Gson();
        System.out.println(gson.fromJson(examMessage, OBDIIReaderTestTemplate.class));
        return result;
    }

}
