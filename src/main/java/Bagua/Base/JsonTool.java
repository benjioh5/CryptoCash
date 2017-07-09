package Bagua.Base;

import org.json.simple.JSONObject;

public class JsonTool
{
    public static JSONObject ParseWithTokenDepth(Object target, String[] Tokens)
    {
        JSONObject object = (JSONObject)target;

        for(String token : Tokens)
        {
            object = (JSONObject)object.get(token);
        }

        return object;
    }
}