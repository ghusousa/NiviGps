package com.nivilive.gps.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.nivilive.gps.data.db.entity.EventEntity;

import java.util.List;

import io.reactivex.Flowable;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface EventDao {

	@Query("SELECT * FROM events ORDER BY date DESC")
	Flowable<List<EventEntity>> getEvents();

	@Insert(onConflict = REPLACE)
	void insertEvent(EventEntity event);

	@Delete
	void deleteEvent(EventEntity event);

	@Query("DELETE FROM events")
	void deleteAll();

}