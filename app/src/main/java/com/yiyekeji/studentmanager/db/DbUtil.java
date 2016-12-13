package com.yiyekeji.studentmanager.db;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.yiyekeji.studentmanager.StuApp;
import com.yiyekeji.studentmanager.bean.Administrator;
import com.yiyekeji.studentmanager.bean.AdministratorDao;
import com.yiyekeji.studentmanager.bean.DaoMaster;
import com.yiyekeji.studentmanager.bean.DaoSession;
import com.yiyekeji.studentmanager.bean.Student;
import com.yiyekeji.studentmanager.bean.StudentDao;
import com.yiyekeji.studentmanager.utils.LogUtil;

import java.util.ArrayList;

import de.greenrobot.dao.query.Query;

/**
 * Created by lxl on 2016/11/4.
 */
public class DbUtil {
    private static DaoMaster.DevOpenHelper helper;
    private static SQLiteDatabase db;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private  static StudentDao studentDao;
    private static AdministratorDao administratorDao;

     static  {
         LogUtil.d("DbUtil","数据库操作工具类正在初始化");
         helper = new DaoMaster.DevOpenHelper(StuApp.getContext(), "student_db", null);
         db = helper.getWritableDatabase();
         daoMaster = new DaoMaster(db);
         daoSession = daoMaster.newSession();
         studentDao = daoSession.getStudentDao();  //拿到这么个工具dao
         administratorDao = daoSession.getAdministratorDao();
         LogUtil.d("DbUtil","数据库操作工具类初始化完成");
    }

    public static   void createAdmin(Administrator administrator){
        administratorDao.insert(administrator);
    }
    public static   void creatStudent(Student  student){
        studentDao.insert(student);
    }


    public static  boolean loginVerfity(String name,String pwd){
        Query query = administratorDao.queryBuilder()
                .where(AdministratorDao.Properties.Name.eq(name),
                        AdministratorDao.Properties.Pwd.eq(pwd))
                .build();
        if (query.list().size()<1){
            return false;
        }
        return true;
    }


    public static ArrayList<Student> queryStudent(String keyword){
        Query query;
        if (TextUtils.isEmpty(keyword)){
            query= studentDao.queryBuilder()
                    .build();
        }else {
            query = studentDao.queryBuilder()
                    .whereOr(StudentDao.Properties.Name.eq(keyword),
                            StudentDao.Properties.StudentId.eq(keyword))
                    .build();
        }
        if (query.list().size()<1){
            return new ArrayList<Student>();
        }
        return (ArrayList<Student>) query.list();
    }
}
