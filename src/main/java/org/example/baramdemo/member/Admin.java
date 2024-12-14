package org.example.baramdemo.member;

public class Admin {

  //관리자 객체를 만들고 로그인 시도 시 관리자 객체와 비교해본다
  private String id;
  private String password;
  private static Admin admin;

  private Admin() {
    id = "admin";
    password = "1234";
  }

  public static Admin getAdmin() {
    if (admin == null) {
      admin = new Admin();
    }
    return admin;
  }

  public String getId() {
    return id;
  }

  public String getPassword() {
    return password;
  }

}
