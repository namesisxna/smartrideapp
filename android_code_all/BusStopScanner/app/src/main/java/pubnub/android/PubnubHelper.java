package pubnub.android;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubException;

/**
 * Created by administrator on 5/17/2016.
 */
public class PubnubHelper {

    private static final String pubKey = "pub-c-d1fd173b-9659-49bb-bd2a-932dbb59b4ed";
    private static final String subKey = "sub-c-98efdd80-aa19-11e5-bb8b-02ee2ddab7fe";

    private static Pubnub pubnub = null;

    public PubnubHelper() {
        pubnub = new Pubnub(pubKey, subKey);
    }

    public void publishString(String channel, String data) {
        pubnub.publish(channel, data, new Callback() {
            @Override
            public void successCallback(String channel, Object message) {
            }
        });
    }

    public void subscribeString(String channel, Callback cback) {
        try {
            pubnub.subscribe(channel, cback);
        } catch (PubnubException e) {
            e.printStackTrace();
        }
    }

    public void unSubscribe(String channel) {
       pubnub.unsubscribe(channel);
    }

}
