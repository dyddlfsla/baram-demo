package org.example.baramdemo.member;

public class Admin {

  //관리자 객체를 만들고 로그인 시도 시 관리자 객체와 비교해본다
  private static final String ID;
  private static final String PASSWORD;
  private static final Admin admin = new Admin();

  static {
    ID = "admin";
    PASSWORD = "1234";
  }

  private Admin() {}

  public static Admin getInstance() {
    return admin;
  }

  public String getId() {
    return ID;
  }

  public String getPassword() {
    return PASSWORD;
  }

}
