package com.board.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// JPA Auditingを有効にするためのアノテーションです。
@EnableJpaAuditing
@SpringBootApplication
public class BoardStudyApplication {
    public static void main(String[] args) {
        // Spring Bootアプリケーションを実行するメソッドです。
        SpringApplication.run(BoardStudyApplication.class, args);
    }
}