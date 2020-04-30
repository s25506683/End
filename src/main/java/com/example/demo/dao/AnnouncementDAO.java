package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Announcement;

public interface AnnouncementDAO {

    public int queryClassHasExists(String cs_id);

    public int hasAtIdExists(int at_id);

    public int postAnnouncement(Announcement announcement);

    public List<Announcement> findAllAnnouncementInTheClass(String cs_id);

    public int updateAnnouncement(Announcement announcement);

    public int deleteAnnouncement(Announcement announcement);

    // public int sendEmailforStudent(String cs_id);

    public String[] findStudentEmail(String cs_id);

    public String findClassName(String cs_id);

}