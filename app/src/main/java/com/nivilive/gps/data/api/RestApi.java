package com.nivilive.gps.data.api;

import com.nivilive.gps.data.model.Device;
import com.nivilive.gps.data.model.Driver;
import com.nivilive.gps.data.model.Event;
import com.nivilive.gps.data.model.Geofence;
import com.nivilive.gps.data.model.Position;
import com.nivilive.gps.data.model.RegisteredUser;
import com.nivilive.gps.data.model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestApi {

//	@GET("positions")
//	Observable<List<Position>> checkLogin(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("session")
    Observable<User> addSession(@Field("email") String email, @Field("password") String password);

	@GET("devices")
	Observable<List<Device>> getDevices(@Header("Authorization") String token);

	@GET("users")
	Observable<List<User>> getUsers(@Header("Authorization") String token);

	@PUT("users/{id}")
	Observable<User> updateUser(@Header("Authorization") String token, @Path("id") int userId, @Body User user);

	@POST("users")
	Observable<User> registerUser(@Body RegisteredUser user);

    @GET("drivers")
	Observable<List<Driver>> getDrivers(@Header("Authorization") String token);

	@GET("positions")
	Observable<List<Position>> getPositions(@Header("Authorization") String token);

	@GET("reports/events?type=geofenceEnter&type=geofenceExit")
	Observable<List<Event>> getEventsByDevice(@Header("Authorization") String token,
	                                          @Query("deviceId") int deviceId,
	                                          @Query("from") String from,
	                                          @Query("to") String to);

	@GET("events/{eventId}")
	Observable<Event> getEvent(@Header("Authorization") String token, @Path("eventId") int eventId);

	@GET("geofences")
	Observable<List<Geofence>> getGeofences(@Header("Authorization") String token);

}