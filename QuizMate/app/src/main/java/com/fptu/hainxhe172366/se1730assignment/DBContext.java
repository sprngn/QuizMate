package com.fptu.hainxhe172366.se1730assignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBContext extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "QuizMate";

    private static final String TB_USER = "user";
    private static final String TB_QUIZ = "quiz";
    private static final String TB_QUESTION = "question";
    private static final String TB_ANSWER = "answer";

    private static final String CREATE_TB_USER =
            "CREATE TABLE user (" +
                    "    user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "    user_name NVARCHAR(50) NOT NULL, " +
                    "    user_email VARCHAR(50) NOT NULL UNIQUE, " +
                    "    user_password VARCHAR(50) NOT NULL, " +
                    "    is_active BIT DEFAULT 0 NOT NULL" +
                    ");";

    private static final String CREATE_TB_QUIZ =
            "CREATE TABLE quiz (" +
                    "    quiz_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "    quiz_name NVARCHAR(100) NOT NULL, " +
                    "    addedDate DATETIME NOT NULL, " +
                    "    is_active BIT DEFAULT 0 NOT NULL, " +
                    "    user_id INTEGER REFERENCES user(user_id)" +
                    ");";

    private static final String CREATE_TB_QUESTION =
            "CREATE TABLE question (" +
                    "    question_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "    quiz_id INTEGER REFERENCES quiz(quiz_id), " +
                    "    question_content VARCHAR(500) NOT NULL, " +
                    "    is_active BIT DEFAULT 0 NOT NULL" +
                    ");";

    private static final String CREATE_TB_ANSWER =
            "CREATE TABLE answer (" +
                    "    answer_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "    question_id INTEGER REFERENCES question(question_id) NOT NULL, " +
                    "    answer_content NVARCHAR(4000) NOT NULL, " +
                    "    is_true BIT DEFAULT 0 NOT NULL" +
                    ");";

    public DBContext(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TB_USER);
        db.execSQL(CREATE_TB_QUIZ);
        db.execSQL(CREATE_TB_QUESTION);
        db.execSQL(CREATE_TB_ANSWER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        if (oldVer <= 1 && newVer >= 2) {
            db.execSQL("DROP TABLE IF EXISTS " + TB_USER);
            db.execSQL("DROP TABLE IF EXISTS " + TB_QUIZ);
            db.execSQL("DROP TABLE IF EXISTS " + TB_QUESTION);
            db.execSQL("DROP TABLE IF EXISTS " + TB_ANSWER);
        }
        onCreate(db);
    }
}
