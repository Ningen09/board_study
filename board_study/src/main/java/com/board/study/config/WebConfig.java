package com.board.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

// ウェブ設定のためのJava Configクラスです。
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    // JSONデータを見やすく表示するためのViewクラスを登録します。
    @Bean
    MappingJackson2JsonView jsonView() {
        // MappingJackson2JsonViewクラスはJSON形式のデータをレンダリングするビューです。
        return new MappingJackson2JsonView();
    }
}