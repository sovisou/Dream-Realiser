package group;

import group.config.*;
import group.controller.*;
import group.mapper.AccountMapper;
import group.mapper.CategoryMapper;
import group.mapper.ContactMapper;
import group.mapper.EventMapper;
import group.repository.AccountRepository;
import group.repository.CategoryRepository;
import group.repository.EventRepository;
import group.service.EventService;
import group.service.PineconeService;
import group.service.impl.CategoryServiceImpl;
import group.service.impl.EventServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;


@SpringBootApplication
// We use direct @Import instead of @ComponentScan to speed up cold starts
// @ComponentScan(basePackages = "group.controller")
@Import({CategoryController.class, EventController.class, EventServiceImpl.class,
AccountController.class, CategoryServiceImpl.class, PaymentController.class, ContactController.class, ChatController.class,
DynamoDBConfig.class, OpenAiConfig.class, PineconeConfig.class, SecurityConfig.class, WebConfig.class,
EventMapper.class, CategoryMapper.class, AccountMapper.class, ContactMapper.class,
AccountRepository.class, CategoryRepository.class, EventRepository.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}