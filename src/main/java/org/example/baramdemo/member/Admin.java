package org.example.baramdemo.member;

public class Admin {

  //관리자 객체를 만들고 로그인 시도 시 관리자 객체와 비교해본다
  private static final String id;
  private static final String password;
  private static final Admin admin = new Admin();

  static {
    id = "admin";
    password = "1234";
  }

  private Admin() {}

  public static Admin getInstance() {
    return admin;
  }

  public String getId() {
    return id;
  }

  public String getPassword() {
    return password;
  }

}
