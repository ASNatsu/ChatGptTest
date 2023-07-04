package com.tencent.wxcloudrun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.tencent.wxcloudrun.dao"})
public class WxCloudRunApplication {  

  public static void main(String[] args) {
    SpringApplication.run(WxCloudRunApplication.class, args);
    System.out.println("(♥◠‿◠)ﾉﾞ  ChatGPT启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
            " .-------.       ____     __        \n" +
            " |  _ _   \\      \\   \\   /  /    \n" +
            " | ( ' )  |       \\  _. /  '       \n" +
            " |(_ o _) /        _( )_ .'         \n" +
            " | (_,_).' __  ___(_ o _)'          \n" +
            " |  |\\ \\  |  ||   |(_,_)'         \n" +
            " |  | \\ `'   /|   `-'  /           \n" +
            " |  |  \\    /  \\      /           \n" +
            " ''-'   `'-'    `-..-'              ");
  }
}
