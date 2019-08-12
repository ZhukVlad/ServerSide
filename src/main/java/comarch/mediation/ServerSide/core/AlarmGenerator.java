package comarch.mediation.ServerSide.core;

import static comarch.mediation.ServerSide.util.ConfigUtil.isClientRegistered;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import comarch.mediation.ServerSide.util.ConfigUtil;
import comarch.mediation.ServerSide.main.RestServer;
import comarch.mediation.ServerSide.model.Alarm;
import comarch.mediation.ServerSide.model.PerceivedSeverity;
import comarch.mediation.ServerSide.util.LoggerUtil;

public class AlarmGenerator extends TimerTask {
	private static LoadingCache<Long, Alarm> cache;
	private static RestServer restClient;
	private Alarm lastGeneratedAlarm;
	
	public AlarmGenerator (Long max_size) {
		cache = CacheBuilder.newBuilder().maximumSize(max_size).build(new CacheLoader<Long, Alarm>() 
		{
			@Override
			public Alarm load(Long id) throws Exception {
				LoggerUtil.debug("Cache: {}", getCache());
				return generateAlarm();
			}
		});
	}
	
	public void run() {
		cache.getUnchecked(ConfigUtil.id);
		if(isClientRegistered) {
			restClient = new RestServer(ConfigUtil.clientHost, HttpClient.globalClient());
			LoggerUtil.debug("Sending new alarm to:  " + ConfigUtil.clientHost);
			Alarm alarm = restClient.sendAlarm(getLastGeneratedAlarm());
			LoggerUtil.debug("Alarm sent: " + Json.serializer().toString(alarm));
		}
	}
	
	public static List<Alarm> getCache(){
		List<Alarm> alarms = new ArrayList<Alarm>(cache.asMap().values());
		return alarms;
	}
	
	public Alarm getLastGeneratedAlarm() {
		return this.lastGeneratedAlarm;
	}
	
	private Alarm generateAlarm() {
		lastGeneratedAlarm = new Alarm(randomEnum(PerceivedSeverity.class), "Test", "TestMO");
		LoggerUtil.debug("New alarm: {}", lastGeneratedAlarm.toString());
		return lastGeneratedAlarm;
	}
	
	private <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = new Random().nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
}
