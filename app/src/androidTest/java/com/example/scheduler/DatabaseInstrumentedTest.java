package com.example.scheduler;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.room.TypeConverters;
import androidx.test.InstrumentationRegistry;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import com.example.scheduler.Model.TasksDAO;
import com.example.scheduler.Model.TasksDatabase;
import com.example.scheduler.Model.TasksTable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseInstrumentedTest {

    private TasksDatabase db ;
    private TasksDAO taskdao ;
    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TasksDatabase.class).build();
        taskdao = db.tasksDAO();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void useAppContext() {
        taskdao.insert(new TasksTable("prova","22-04-1997 08:56", "Pending","Homework","Important"));
        taskdao.insert(new TasksTable("prova2","22-04-1997 09:40", "Pending","Homework","Urgent"));
        //assertEquals("Get",t.getDate(),taskdao.getTasksWithPriority("Urgent").get(0).getDate());

        TasksTable t = taskdao.getTaskWithPrimaryKey("22-04-1997 08:56","prova");
        taskdao.deleteTaskWithPrimaryKey("22-04-1997 08:56","prova");
        t = taskdao.getTaskWithPrimaryKey("22-04-1997 08:56","prova");
    }
}
