package com.example.demo.entity;

import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.security.Timestamp;
import java.sql.Date;

public class Acceptance {

 private int accept_id;
 private int accept_hw_id;
 private int accept_score;
 private boolean accept_done;
 private Time accept_time;
 private String hw_cs_id;
 private String hw_name;
 private String hw_createtime;
 private String hw_content;
 private String cs_id;
 private int teacher_id;
 private int std_id;
 private int hw_id;

 private String std_name;

 private int accept_state;
 private String accept_content;
 private int accept_tag;
 private int hw_closed;



 public int getHw_id() {
     return this.hw_id;
 }

 public void setHw_id(int hw_id) {
     this.hw_id = hw_id;
 }

 public int getStd_id() {
     return this.std_id;
 }

 public void setStd_id(int std_id) {
     this.std_id = std_id;
 }

 public String getCs_id() {
     return this.cs_id;
 }

 public void setCs_id(String cs_id) {
     this.cs_id = cs_id;
 }

 public int getTeacher_id() {
     return this.teacher_id;
 }

 public void setTeacher_id(int teacher_id) {
     this.teacher_id = teacher_id;
 }

 public String getHw_content() {
     return this.hw_content;
 }

 public void setHw_content(String hw_content) {
     this.hw_content = hw_content;
 }

 public String getHw_createtime() {
     return this.hw_createtime;
 }

 public void setHw_createtime(String hw_createtime) {
     this.hw_createtime = hw_createtime;
 }



 public String getHw_cs_id() {
     return this.hw_cs_id;
 }

 public void setHw_cs_id(final String hw_cs_id) {
     this.hw_cs_id = hw_cs_id;
 }

 public String getHw_name() {
     return this.hw_name;
 }

 public void setHw_name(final String hw_name) {
     this.hw_name = hw_name;
 }

 public int getAccept_id() {
  return accept_id;
 }

 public void setAccept_id(final int accept_id) {
  this.accept_id = accept_id;
 }

 public int getAccept_hw_id() {
  return accept_hw_id;
 }

 public void setAccept_hw_id(final int accept_hw_id) {
  this.accept_hw_id = accept_hw_id;
 }

 public String getStd_name() {
     return this.std_name;
 }

 public void setStd_name(String std_name) {
     this.std_name = std_name;
 }

 public Time getAccept_time() {
  return accept_time;
 }

 public void setAccept_time(final Time accept_time) {
  this.accept_time = accept_time;
 }
 
 public int getAccept_score() {
  return accept_score;
 }

 public void setAccept_score(final int accept_score) {
  this.accept_score = accept_score;
 }

 public boolean isAccept_done() {
  return accept_done;
 }

 public void setAccept_done(final boolean accept_done) {
  this.accept_done = accept_done;
 }
 public int getAccept_state() {
     return this.accept_state;
 }

 public void setAccept_state(int accept_state) {
     this.accept_state = accept_state;
 }

 public String getAccept_content() {
     return this.accept_content;
 }

 public void setAccept_content(String accept_content) {
     this.accept_content = accept_content;
 }

 public int getAccept_tag() {
     return this.accept_tag;
 }

 public void setAccept_tag(int accept_tag) {
     this.accept_tag = accept_tag;
 }

 public int getHw_closed() {
     return this.hw_closed;
 }

 public void setHw_closed(int hw_closed) {
     this.hw_closed = hw_closed;
 }


}


